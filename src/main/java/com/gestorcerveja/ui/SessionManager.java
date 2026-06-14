package com.gestorcerveja.ui;
import com.gestorcerveja.model.Usuario;
public class SessionManager {
    private static String  currentRole="ADMIN";
    private static Usuario currentUser=null;
    public static String  getRole()            {return currentRole;}
    public static void    setRole(String role) {currentRole=role==null?"ADMIN":role.toUpperCase();}
    public static Usuario getUser()              {return currentUser;}
    public static void    setUser(Usuario user)  {currentUser=user;}
    public static String  getUserNome()          {return currentUser!=null?currentUser.getNome():"Utilizador";}
    public static void    clear()                {currentRole="ADMIN";currentUser=null;}
    public static String  getRoleLabel()         {return getRoleLabelNormalize(currentRole);}
    public static String  getRoleLabelNormalize(String role){if(role==null)return "Utilizador";return switch(role.toUpperCase()){case "ADMIN"->"Administrador";case "PRODUCAO"->"Gestor de Produção";case "OPERADOR"->"Operador";case "QUALIDADE"->"Qualidade";case "ARMAZEM"->"Armazém";case "COMERCIAL"->"Comercial";default->"Utilizador";};}
    public static String[][] getNavGroups(){return switch(currentRole.toUpperCase()){case"ADMIN"->new String[][]{{"Principal","dashboard","Dashboard"},{"Comercial","pedidos","Pedidos","clientes","Clientes","faturas","Faturas"},{"Produção","receitas","Receitas","lotes","Lotes","producao","Etapas de Produção","ingredientes","Ingredientes"},{"Logística","veiculos","Veículos"},{"Sistema","utilizadores","Utilizadores"}};case"PRODUCAO"->new String[][]{{"Principal","dashboard","Dashboard"},{"Produção","lotes","Lotes","producao","Etapas de Produção","receitas","Receitas","ingredientes","Ingredientes"},{"Pedidos","pedidos","Pedidos"}};case"OPERADOR"->new String[][]{{"Principal","dashboard","Dashboard"},{"Trabalho","producao","Etapas de Produção","lotes","Lotes"}};case"ARMAZEM"->new String[][]{{"Principal","dashboard","Dashboard"},{"Armazém","ingredientes","Ingredientes","stock","Stock"}};case"COMERCIAL"->new String[][]{{"Principal","dashboard","Dashboard"},{"Comercial","pedidos","Pedidos","clientes","Clientes","faturas","Faturas"}};default->new String[][]{{"Principal","dashboard","Dashboard"}};};}
}
