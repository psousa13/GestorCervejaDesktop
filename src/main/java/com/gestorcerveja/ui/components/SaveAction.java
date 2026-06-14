package com.gestorcerveja.ui.components;
import java.util.Map;
@FunctionalInterface public interface SaveAction { void execute(Map<String,String> values) throws Exception; }
