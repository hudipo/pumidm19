package com.hudipo.pum_indomaret.model;

public class OptionItem {
    private Object key;
    private Object icon;
    private String label;
    private Object value;

    public OptionItem(Object key, Object icon, String label, Object value) {
        this.key = key;
        this.icon = icon;
        this.label = label;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
