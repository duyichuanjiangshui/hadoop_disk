package com.liangrui.hadoop_disk.handler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GlobalErrorHandler implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        int status = response.getStatus();

        String message;
        String page = "error";

        switch (status) {
            case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
                message = "服务器异常";
                break;
            case HttpServletResponse.SC_NOT_FOUND:
                message = "页面丢失了";
                break;
            case HttpServletResponse.SC_UNAUTHORIZED:
                message = "未认证";
                break;
            case HttpServletResponse.SC_FORBIDDEN:
                throw new RuntimeException("接口拒绝访问，用户权限不足");
            default:
                message = "其他错误发生了";
        }
        model.addAttribute("msg", message);
        return page;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
