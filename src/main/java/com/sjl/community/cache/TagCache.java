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
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java", "c/c++", "python", "golang", "c#", "php", "javascript", "scala", "html", "html5", "css", "node·js", "objective-c", "typescript", "shell", "swift", "sass", "ruby", "bash", "less", "asp·net", "lua", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDtos.add(program);

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring", "springmvc", "springboot", "springcloud", "mybatis", "bootstrap", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts", "laravel"));
        tagDtos.add(framework);


        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDtos.add(server);

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql", "memcached", "elasticsearch", "sqlserver", "postgresql", "sqlite"));
        tagDtos.add(db);

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("intellij-idea", "git", "github", "vscode", "vim", "sublime", "eclipse", "xcode", "maven", "ide", "svn", "android-studio", "atom", "emacs", "textmate", "hg"));
        tagDtos.add(tool);

        TagDto other = new TagDto();
        other.setCategoryName("其它");
        other.setTags(Arrays.asList("找bug", "测试", "冒泡", "交友", "生活", "电影", "音乐", "读书", "美食", "游戏", "科技", "数码", "理财"));
        tagDtos.add(other);
        return tagDtos;
    }


}
