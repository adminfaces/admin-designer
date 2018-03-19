package com.github.adminfaces.showcase.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by rmpestano on 07/05/17.
 */
@Named
@SessionScoped
public class LayoutMB implements Serializable {

    private String template;

    private boolean flat;

    private boolean darkSidebar;

    @PostConstruct
    public void init() {
        setDefaultTemplate();
        flat = false;
        darkSidebar = true;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplateTop() {
        template = "/WEB-INF/templates/template-top.xhtml";
    }

    public void setDefaultTemplate() {
        template = "/WEB-INF/templates/template.xhtml";
    }

    public void toogleTemplate() {
        if(isDefaultLayout()) {
            setTemplateTop();
        } else {
            setDefaultTemplate();
        }
    }

    public boolean isDefaultLayout() {
        return template != null && template.endsWith("template.xhtml");
    }

    public boolean isFlat() {
        return flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }

    public boolean isDarkSidebar() {
        return darkSidebar;
    }

    public void setDarkSidebar(boolean darkSidebar) {
        this.darkSidebar = darkSidebar;
    }
}
