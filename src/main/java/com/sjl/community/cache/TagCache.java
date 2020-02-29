package com.sjl.community.cache;

import com.sjl.community.dto.TagDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author song
 * @create 2020/2/22 19:38
 */
public class TagCache {

    public static List<TagDto> get() {
        List<TagDto> tagDTOS = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "css", "html", "html5", "php", "java", "node·js", "python", "c艹", "c", "golang", "objective-c", "typescript", "shell", "swift", "c井", "sass", "ruby", "bash", "less", "asp·net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "mybatis", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);


        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(server);

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql", "memcached", "sqlserver", "postgresql", "sqlite", "elasticsearch"));
        tagDTOS.add(db);

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode", "intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom", "emacs", "textmate", "hg"));
        tagDTOS.add(tool);

        TagDto other = new TagDto();
        tool.setCategoryName("其它");
        tool.setTags(Arrays.asList("找bug", "测试", "冒泡", "交友", "生活", "电影", "音乐", "读书", "美食", "游戏", "科技", "数码", "理财"));
        tagDTOS.add(other);
        return tagDTOS;
    }


}
