package com.liangrui.hadoop_disk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.dto.LayuiTree;
import com.liangrui.hadoop_disk.bean.entity.Folder;
import com.liangrui.hadoop_disk.bean.model.PageModel;
import com.liangrui.hadoop_disk.service.FileAndFolderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fileAndFolder")
public class FolderAndFileController {
    /*
     * 这是关于文件夹的controller
     * */
    //全部文件索引
    @Autowired
    private FileAndFolderService fileAndFolderService;

    @RequestMapping("/allFile")
    public String allFile(Model model, int fatherFolderid) {

        model.addAttribute("fatherFolderid", fatherFolderid);
        return "allFile";
    }

    //查看所有文件夹
    @RequestMapping("/allFolder")
    @ResponseBody
    public Map<String, Object> allFolder(PageModel<List<FileAndFolderDto>> page, Model model, int fatherFolder) {
        int userid = 1;//从session里得
        List<FileAndFolderDto> list = fileAndFolderService.findAllFileAndFolder(fatherFolder, userid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("count", page.getCount());
        data.put("data", list);
        model.addAttribute("fatherid", fatherFolder);
        return data;
    }

    //添加文件夹
    @RequestMapping("/addFolder")
    @ResponseBody
    public Map<String, Object> addFolder(@Param("filename") String filename, @Param("fatherFolderid") Integer fatherFolderid) {
        int userid = 1;//session

        Map<String, Object> rs = new HashMap<>();
        if (fileAndFolderService.isexist(filename, userid, fatherFolderid)) {
            rs.put("code", 1);
            rs.put("msg", "文件夹名不可重复");
            return rs;
        }
        int result = fileAndFolderService.addFolder(filename, fatherFolderid, userid);
        if (result == 0) {
            rs.put("code", 1);
            rs.put("msg", "添加失败");
        } else {
            rs.put("code", 0);
            rs.put("msg", "添加成功");
        }
        return rs;
    }

    @RequestMapping("/delFolder")
    @ResponseBody
    public Map<String, Object> userDelete(int folderid, int type) {

        Map<String, Object> rs = new HashMap<>();
        if (type == 0) {
            fileAndFolderService.delteFolder(folderid);
            rs.put("code", 0);
            rs.put("msg", "删除成功");
        } else {
            fileAndFolderService.deleteFile(folderid);
            rs.put("code", 0);
            rs.put("msg", "删除成功");
        }
        return rs;
    }

    //重名
    @RequestMapping("/renameFolder")
    @ResponseBody
    public Map<String, Object> updateFolder(String filename, int folderid, int type) {
        int userid = 1;//session
        Map<String, Object> rs = new HashMap<>();
        if (type == 0) {
            if (fileAndFolderService.isexist(filename, userid, folderid)) {
                rs.put("code", 1);
                rs.put("msg", "文件夹名不可重复");
                return rs;
            }
            int result = fileAndFolderService.updateFoler(filename, folderid);
            if (result == 0) {
                rs.put("code", 1);
                rs.put("msg", "更新失败");
            } else {
                rs.put("code", 0);
                rs.put("msg", "更新成功");
            }

        } else {
            if (fileAndFolderService.isexistfile(filename, userid, folderid)) {
                rs.put("code", 1);
                rs.put("msg", "文件名不可重复");
                return rs;
            }
            int result = fileAndFolderService.updateFileName(filename, folderid);
            if (result == 0) {
                rs.put("code", 1);
                rs.put("msg", "更新失败");
            } else {
                rs.put("code", 0);
                rs.put("msg", "更新成功");
            }
        }
        return rs;

    }

    @RequestMapping("/changefileStaus")
    @ResponseBody
    public Map<String, Object> changefileStaus(@Param("sharetype") int sharetype, @Param("folderid") int folderid, @Param("type") int type) {
        Map<String, Object> rs = new HashMap<>();
        int result = fileAndFolderService.changesharetype(sharetype, type, folderid);
        if (result == 0) {
            rs.put("code", 1);
            rs.put("msg", "更新失败");
        } else {
            rs.put("code", 0);
            rs.put("msg", "更新成功");
        }
        return rs;
    }

    @RequestMapping("/onlyfolder")
    public String onlyfolder(Model model, int folderid, int type, int altertype) {
        model.addAttribute("folderid", folderid);
        model.addAttribute("type", type);
        model.addAttribute("altertype", altertype);
        System.out.println(altertype);
        return "folder";
    }

    @RequestMapping("/onlyfolderlist")
    public String onlyfolderlist(Model model,@Param("altertype") String altertype)
    {
        model.addAttribute("altertype", altertype);
        System.out.println(altertype);
        return "listtofolder";
    }


    @RequestMapping("/copyormoveall")
    @ResponseBody
    public Map<String, Object> copyfileall( @Param("objects") String objects,@Param("altertype") int altertype,@Param("aimFolderid") int aimFolderid) {
        System.out.println(objects);
        System.out.println("Sdf");
        int userid = 1;//session
        Map<String, Object> rs = new HashMap<>();
        List<FileAndFolderDto> list=JSONObject.parseArray(objects,FileAndFolderDto.class);
        if (!fileAndFolderService.allcanmoveorcopy(list, userid,aimFolderid)) {
            rs.put("code", 1);
            rs.put("msg", "不可以选择子文件或者自身");
        } else {
            if (fileAndFolderService.isexitMoveOrCopyNmaeinlist(list, altertype, aimFolderid, userid)) {
                rs.put("code", 1);
                rs.put("msg", "文件名不可重复");
            } else {

                if (altertype == 0)//标志着是复制文件
                {
                    for(FileAndFolderDto fileAndFolderDto:list)
                    {
                    if (fileAndFolderDto.getType() == 0) {
                        fileAndFolderService.copyFolder(fileAndFolderDto.getId(), aimFolderid, userid);
                    } else {
                        fileAndFolderService.copyFile(fileAndFolderDto.getId(), aimFolderid, userid);
                    }
                    }
                    rs.put("code", 0);
                    rs.put("msg", "复制成功");
                }else {
                    for(FileAndFolderDto fileAndFolderDto:list)
                    {
                        fileAndFolderService.moverFileAndFolder(fileAndFolderDto.getId(),fileAndFolderDto.getType(),aimFolderid);
                    }
                    rs.put("code", 0);
                    rs.put("msg", "移动成功");
                }
            }
        }
        return rs;
    }

    @RequestMapping("/copyormove")
    @ResponseBody
    //type=0代表是文件夹
    public Map<String, Object> copyfile(@Param("folderid") int folderid, @Param("type") int type, @Param("altertype") Integer altertype, @Param("aimFolderid") int aimFolderid) {
        int userid = 1;//session
        Map<String, Object> rs = new HashMap<>();
        if (!fileAndFolderService.canmoveorcopy(folderid, type, altertype, aimFolderid, userid)) {
            rs.put("code", 1);
            rs.put("msg", "不可以选择子文件或者自身");
        } else {
            if (fileAndFolderService.isexitMoveOrCopyNmae(folderid, type, altertype, aimFolderid, userid)) {
                rs.put("code", 1);
                rs.put("msg", "文件名不可重复");
            } else {
                if (altertype == 0)//标志着是复制文件
                {
                    if (type == 0) {
                        fileAndFolderService.copyFolder(folderid, aimFolderid, userid);
                    } else {
                        fileAndFolderService.copyFile(folderid, aimFolderid, userid);
                    }
                    rs.put("code", 0);
                    rs.put("msg", "复制成功");
                }else {
                    fileAndFolderService.moverFileAndFolder(folderid,type,aimFolderid);
                    rs.put("code", 0);
                    rs.put("msg", "移动成功");
                }
            }
        }
        return rs;
    }


    @RequestMapping(value = "/treeload")
    @ResponseBody
    public Object findTypeTree() throws Exception {
        int userid = 1;
        List<FolderDto> folderDtos = fileAndFolderService.getFolderTree(userid);//查出所有部门
        List<LayuiTree> list = new ArrayList<>();
        LayuiTree treeObject = new LayuiTree();
        treeObject.setId("0");
        treeObject.setName("全部文件");
        treeObject.setChildren(getChildren(0, folderDtos));
        list.add(treeObject);
        return JSON.toJSON(list);
    }

    public List<LayuiTree> getChildren(Integer parentId, List<FolderDto> folderDtos) {
        List<LayuiTree> list = new ArrayList<>();
        for (FolderDto t : folderDtos) {
            if (t.getFatherfolderid().equals(parentId)) {
                LayuiTree treeObject = new LayuiTree();
                treeObject.setId(t.getFolderid().toString());
                treeObject.setName(t.getName());
                treeObject.setChildren(getChildren(t.getFolderid(), folderDtos));
                list.add(treeObject);
            }
        }
        return list;
    }
    @RequestMapping("/delall")
    @ResponseBody
    public Map<String, Object> delall( String objects)
    {
        List<FileAndFolderDto> list=JSONObject.parseArray(objects,FileAndFolderDto.class);
        Map<String, Object> rs = new HashMap<>();
        for(FileAndFolderDto fileAndFolderDto:list)
        {
            if (fileAndFolderDto.getType() == 0) {
                fileAndFolderService.delteFolder(fileAndFolderDto.getId());
            } else {
                fileAndFolderService.deleteFile(fileAndFolderDto.getId());
            }
        }
        rs.put("code", 0);
        rs.put("msg", "删除成功");
        return  rs;
    }



}
