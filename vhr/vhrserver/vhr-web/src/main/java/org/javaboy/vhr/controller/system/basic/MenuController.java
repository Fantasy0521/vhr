package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.Menu;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("/system/basic/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @PostMapping("/")
    public RespBean addMenu(@RequestBody Menu menu) {
        if (menuService.addMenu(menu) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @PutMapping("/")
    public RespBean updateMenu(@RequestBody Menu menu) {
        if (menuService.updateMenu(menu) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }


    @DeleteMapping("/{id}")
    public RespBean deleteMenuById(@PathVariable Integer id) {
        if (menuService.deleteMenuById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @GetMapping("/getByParentId")
    public List<Menu> getByParentId(Integer pid) {
        return menuService.getByParentId(pid);
    }



}
