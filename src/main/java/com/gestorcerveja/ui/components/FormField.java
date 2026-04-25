package com.gestorcerveja.ui.components;

import java.util.List;

/**
 * Descriptor de um campo de formulário usado pelo {@link ModalOverlay}.
 */
public class FormField {

    public enum Type { TEXT, NUMBER, DATE, COMBO, PASSWORD }

    private final String       key;
    private final String       label;
    private final Type         type;
    private final List<String> options;
    private final String       placeholder;

    public FormField(String key, String label, Type type) {
        this(key, label, type, List.of(), "");
    }

    public FormField(String key, String label, Type type, List<String> options) {
        this(key, label, type, options, "");
    }

    public FormField(String key, String label, Type type,
                     List<String> options, String placeholder) {
        this.key         = key;
        this.label       = label;
        this.type        = type;
        this.options     = options;
        this.placeholder = placeholder;
    }

    public String       getKey()         { return key; }
    public String       getLabel()       { return label; }
    public Type         getType()        { return type; }
    public List<String> getOptions()     { return options; }
    public String       getPlaceholder() { return placeholder; }
}
