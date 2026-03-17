package edu.jiangbaiyu.demo.spring.thymeleaf.practice.controller;

import edu.jiangbaiyu.demo.spring.thymeleaf.practice.entity.User;
import edu.jiangbaiyu.demo.spring.thymeleaf.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 用户Web控制器
 * @author Robin
 */
@Controller
@RequestMapping("/users")
public class UserWebController {

    @Autowired
    private UserService userService;

    /**
     * 用户列表页
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    /**
     * 跳转到新增表单
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    /**
     * 保存新增用户
     */
    @PostMapping
    public String create(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "用户创建成功");
        return "redirect:/users";
    }

    /**
     * 跳转到编辑表单
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "users/form";
    }

    /**
     * 更新用户（使用POST + _method=PUT 模拟PUT）
     */
    @PutMapping("/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute User user, RedirectAttributes redirectAttributes) {
        user.setId(id);
        userService.update(user);
        redirectAttributes.addFlashAttribute("message", "用户更新成功");
        return "redirect:/users";
    }

    /**
     * 删除用户
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "用户删除成功");
        return "redirect:/users";
    }
}