package cn.com.xuxiaowei.redissession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Index Controller
 *
 * @author xuxiaowei
 */
@Controller
public class IndexController {

    /**
     * 主页
     */
    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {

        HttpSession session = request.getSession();

        // Session ID
        String sessionId = session.getId();

        // Session 创建时间戳
        long creationTime = session.getCreationTime();

        // 系统默认时区时间
        LocalDateTime sessionCreationTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(creationTime), ZoneId.systemDefault());

        // 放入页面
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("sessionCreationTime", sessionCreationTime);

        return "index";
    }


}
