package com.liangrui.hadoop_disk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangrui.hadoop_disk.bean.dto.FileAndFolderDto;
import com.liangrui.hadoop_disk.bean.dto.FolderDto;
import com.liangrui.hadoop_disk.bean.dto.LayuiTree;
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
    public String allFile(Model model, String fatherFolderid) {
        System.out.println(fatherFolderid);

        model.addAttribute("fatherFolderid", fatherFolderid);
        return "allFile";
    }

    //查看所有文件夹
    @RequestMapping("/allFolder")
    @ResponseBody
    public Map<String, Object> allFolder(PageModel<List<FileAndFolderDto>> page, Model model, String fatherFolder) {
        List<FileAndFolderDto> list = fileAndFolderService.findAllFileAndFolder(fatherFolder);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("count", page.getCount());
        data.put("data", list);
        model.addAttribute("fatherid", fatherFolder);
        return data;
    }
    @RequestMapping("/searchpage")
    public String searchpage(Model model, String name) {
        model.addAttribute("name",name);
        return "search";
    }
    @RequestMapping("/searchbylikename")
    @ResponseBody
    public Map<String, Object> searchbylikename(PageModel<List<FileAndFolderDto>> page, String name) {
        int userid = 1;//从session里得
        String rootFolderid="root";
        System.out.println(name);
        List<FileAndFolderDto> list = fileAndFolderService.findByLikeName(userid,name,rootFolderid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("count", page.getCount());
        data.put("data", list);
        return data;
    }
    @RequestMapping("/alonetypefile")
    public String alonetypefile(Model model,int type) {
        model.addAttribute("type",type);
        return "alonetypefile";
    }
    @RequestMapping("/findalonetypefile")
    @ResponseBody
    public Map<String, Object> findalonetypefile(PageModel<List<FileAndFolderDto>> page, int type) {
        int userid = 1;//从session里得
        String rootFolderid="root";
        List<FileAndFolderDto> list = fileAndFolderService.findByFiltype(userid,type,rootFolderid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("count", page.getCount());
        data.put("data", list);
        return data;
    }
    @RequestMapping("/searchalonetypefile")
    @ResponseBody
    public Map<String, Object> searchalonetypefile(PageModel<List<FileAndFolderDto>> page, int type,String name) {
        int userid = 1;//从session里得
        String rootFolderid="root";
        List<FileAndFolderDto> list = fileAndFolderService.findalonefileByFiltype(userid,type,name,rootFolderid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("count", page.getCount());
        data.put("data", list);
        return data;
    }
    //添加文件夹
    @RequestMapping("/addFolder")
    @ResponseBody
    public Map<String, Object> addFolder(@Param("filename") String filename, @Param("fatherFolderid") String fatherFolderid) {
        int userid = 1;//session

        Map<String, Object> rs = new HashMap<>();
        if (fileAndFolderService.isexist(filename, fatherFolderid)) {
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
    public Map<String, Object> userDelete(String folderid, int type) {

        Map<String, Object> rs = new HashMap<>();
        if (type == 0) {
            fileAndFolderService.delteFolder(folderid);
            rs.put("code", 0);
            rs.put("msg", "删除成功");
        } else {
            fileAndFolderService.deleteFile(Integer.parseInt(folderid));
            rs.put("code", 0);
            rs.put("msg", "删除成功");
        }
        return rs;
    }

    //重名
    @RequestMapping("/renameFolder")
    @ResponseBody
    public Map<String, Object> updateFolder(String filename, String folderid, int type) {
        int userid = 1;//session
        Map<String, Object> rs = new HashMap<>();
        if (type == 0) {
            if (fileAndFolderService.isexist(filename,folderid)) {
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
            if (fileAndFolderService.isexistfile(filename,folderid)) {
                rs.put("code", 1);
                rs.put("msg", "文件名不可重复");
                return rs;
            }
            int result = fileAndFolderService.updateFileName(filename, Integer.parseInt(folderid));
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
    public Map<String, Object> changefileStaus(@Param("sharetype") int sharetype, @Param("folderid") String folderid, @Param("type") int type) {
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
    public String onlyfolder(Model model, String folderid, int type, int altertype) {
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
    public Map<String, Object> copyfileall( @Param("objects") String objects,@Param("altertype") int altertype,@Param("aimFolderid") String aimFolderid) {
        System.out.println(objects);
        int userid = 1;//session
        Map<String, Object> rs = new HashMap<>();
        List<FileAndFolderDto> list=JSONObject.parseArray(objects,FileAndFolderDto.class);
        if (!fileAndFolderService.allcanmoveorcopy(list, userid,aimFolderid)) {
            rs.put("code", 1);
            rs.put("msg", "不可以选择子文件或者自身");
        } else {
            if (fileAndFolderService.isexitMoveOrCopyNmaeinlist(list, userid, aimFolderid)) {
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
    public Map<String, Object> copyfile(@Param("folderid") String folderid, @Param("type") int type, @Param("altertype") Integer altertype, @Param("aimFolderid") String aimFolderid) {
        int userid = 1;//session
        String rootFolderid="root";
        Map<String, Object> rs = new HashMap<>();
        if (!fileAndFolderService.canmoveorcopy(folderid, type, altertype, aimFolderid, userid)) {
            rs.put("code", 1);
            rs.put("msg", "不可以选择子文件或者自身");
        } else {
            if (fileAndFolderService.isexitMoveOrCopyNmae(folderid, type, aimFolderid, userid)) {
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
        String rootFolderid="root";
        List<FolderDto> folderDtos = fileAndFolderService.getFolderTree(userid);//查出所有部门
        List<LayuiTree> list = new ArrayList<>();
        LayuiTree treeObject = new LayuiTree();

        treeObject.setId(rootFolderid);
        treeObject.setName("全部文件");
        treeObject.setChildren(getChildren(rootFolderid, folderDtos));
        list.add(treeObject);
        return JSON.toJSON(list);
    }

    public List<LayuiTree> getChildren(String parentId, List<FolderDto> folderDtos) {
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
                fileAndFolderService.deleteFile(Integer.parseInt(fileAndFolderDto.getId()));
            }
        }
        rs.put("code", 0);
        rs.put("msg", "删除成功");
        return  rs;
    }

    //回收站
    @RequestMapping("/recyclepage")
    public String recyclepage() {
        return "recycler";
    }
    @RequestMapping("/findrecyclefile")
    @ResponseBody
    public Map<String, Object> findrecyclefile(PageModel<List<FileAndFolderDto>> page) {
        int userid = 1;//从session里得
        String rootFolderid="root";
        List<FileAndFolderDto> list = fileAndFolderService.findrecyclerfile(userid,rootFolderid);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "查询完成");
        data.put("count", page.getCount());
        data.put("data", list);
        return data;
    }
    @RequestMapping("/delrecycle")
    @ResponseBody
    public Map<String, Object> delrecycle(String fileid, int type) {

            int userid=1;//session
             Map<String, Object> rs = new HashMap<>();
            fileAndFolderService.delterecycle(userid,fileid,type);
            rs.put("code", 0);
            rs.put("msg", "删除成功");

        return rs;
    }
    @RequestMapping("/delrecycleall")
    @ResponseBody
    public Map<String, Object> delrecycleall(String objects) {

        int userid=1;//session
        List<FileAndFolderDto> list=JSONObject.parseArray(objects,FileAndFolderDto.class);
        Map<String, Object> rs = new HashMap<>();
        for(FileAndFolderDto fileAndFolderDto:list)
        {
            fileAndFolderService.delterecycle(userid,fileAndFolderDto.getId(),fileAndFolderDto.getType());
        }
        rs.put("code", 0);
        rs.put("msg", "删除成功");

        return rs;
    }
    @RequestMapping("/recycle")
    @ResponseBody
    public Map<String, Object> recycle(@Param("objects") String objects) {
        JSONObject jsonObject=JSON.parseObject(objects);
        FileAndFolderDto fileAndFolderDto=JSON.toJavaObject(jsonObject,FileAndFolderDto.class);
        int  userid=1;//session
        System.out.println(objects);
        Map<String, Object> rs = new HashMap<>();
        if (fileAndFolderDto.getType() == 0) {
            if (fileAndFolderService.isexist(fileAndFolderDto.getName(),  fileAndFolderDto.getFatherfolderid())) {
                rs.put("code", 1);
                rs.put("msg", "文件夹名不可重复");
                return rs;
            }
            int result = fileAndFolderService.recycle(userid,fileAndFolderDto.getId(),fileAndFolderDto.getType());
            if (result == 0) {
                rs.put("code", 1);
                rs.put("msg", "还原失败");
            } else {
                rs.put("code", 0);
                rs.put("msg", "还原成功");
            }

        } else {
            if (fileAndFolderService.isexist(fileAndFolderDto.getName(),  fileAndFolderDto.getFatherfolderid())) {
                rs.put("code", 1);
                rs.put("msg", "文件名不可重复");
                return rs;
            }
            int result = fileAndFolderService.recycle(userid,fileAndFolderDto.getId(),fileAndFolderDto.getType());
            if (result == 0) {
                rs.put("code", 1);
                rs.put("msg", "还原失败");
            } else {
                rs.put("code", 0);
                rs.put("msg", "还原成功");
            }
        }
        return rs;
    }

    @RequestMapping("/recycleall")
    @ResponseBody
    public Map<String, Object> recycleall(@Param("objects") String objects) {


        int userid=1;//session
        Map<String, Object> rs = new HashMap<>();
        List<FileAndFolderDto> list=JSONObject.parseArray(objects,FileAndFolderDto.class);
        //先判断是否可以还原
        for(FileAndFolderDto fileAndFolderDto:list)
        {
            if (fileAndFolderDto.getType() == 0) {
                if (fileAndFolderService.isexist(fileAndFolderDto.getName(),fileAndFolderDto.getFatherfolderid())) {
                    rs.put("code", 1);
                    rs.put("msg", "文件夹名不可重复");
                    return rs;
                }
            } else {
                if (fileAndFolderService.isexist(fileAndFolderDto.getName(), fileAndFolderDto.getFatherfolderid())) {
                    rs.put("code", 1);
                    rs.put("msg", "文件名不可重复");
                    return rs;
                }
            }
        }
        //可以还原将他们一个一个还原
        for(FileAndFolderDto fileAndFolderDto:list)
        {
            fileAndFolderService.recycle(userid,fileAndFolderDto.getId(),fileAndFolderDto.getType());
        }
        rs.put("code", 0);
        rs.put("msg", "还原成功");
        return rs;
    }

}
