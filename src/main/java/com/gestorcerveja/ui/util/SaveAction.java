package com.gestorcerveja.ui.util;
import java.util.Map;
@FunctionalInterface public interface SaveAction { void execute(Map<String,String> values) throws Exception; }
