package com.gestorcerveja.ui.util;
import javafx.scene.control.*;
import javafx.stage.FileChooser; import javafx.stage.Window;
import java.io.*; import java.util.List; import java.util.stream.Collectors;

public final class ExportUtils {
    private ExportUtils(){}
    public static <T> void export(TableView<T> table,Window owner,String baseName){
        FileChooser fc=new FileChooser();fc.setTitle("Exportar dados");fc.setInitialFileName(baseName);
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV","*.csv"),new FileChooser.ExtensionFilter("Texto","*.txt"));
        File file=fc.showSaveDialog(owner);if(file==null)return;
        boolean isCsv=file.getName().toLowerCase().endsWith(".csv");
        try(PrintWriter pw=new PrintWriter(new FileWriter(file))){
            List<TableColumn<T,?>> cols=table.getColumns().stream().flatMap(c->getLeaves(c).stream()).collect(Collectors.toList());
            pw.println(buildRow(cols.stream().map(TableColumn::getText).collect(Collectors.toList()),isCsv));
            for(T item:table.getItems())pw.println(buildRow(cols.stream().map(col->{var cd=col.getCellObservableValue(item);if(cd==null)return "";Object v=cd.getValue();return v==null?"":v.toString();}).collect(Collectors.toList()),isCsv));
            new Alert(Alert.AlertType.INFORMATION,"Exportado para:\n"+file.getAbsolutePath()).showAndWait();
        }catch(IOException ex){new Alert(Alert.AlertType.ERROR,"Erro: "+ex.getMessage()).showAndWait();}
    }
    private static String buildRow(List<String> cells,boolean csv){return csv?cells.stream().map(v->"\""+v.replace("\"","\"\"")+"\"").collect(Collectors.joining(",")):String.join("\t",cells);}
    @SuppressWarnings("unchecked")
    private static <T> List<TableColumn<T,?>> getLeaves(TableColumn<T,?> col){return col.getColumns().isEmpty()?List.of(col):col.getColumns().stream().flatMap(c->getLeaves((TableColumn<T,?>)c).stream()).collect(Collectors.toList());}
}
