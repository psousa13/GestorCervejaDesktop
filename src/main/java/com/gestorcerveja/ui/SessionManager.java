package com.gestorcerveja.ui;

public class SessionManager {

    private static String currentRole = "admin";

    public static String getRole() { return currentRole; }
    public static void setRole(String role) { currentRole = role; }

    public static String getRoleLabel() { return getRoleLabel(currentRole); }

    public static String getRoleLabel(String role) {
        return switch (role) {
            case "admin"     -> "Administrador";
            case "producao"  -> "Gestor de Produção";
            case "operador"  -> "Operador de Produção";
            case "qualidade" -> "Gestor de Qualidade";
            case "armazem"   -> "Resp. de Armazém";
            case "comercial" -> "Comercial";
            default          -> "Utilizador";
        };
    }

    public static String[][] getNavGroups() {
        return switch (currentRole) {
            case "admin" -> new String[][]{
                    {"Principal",  "dashboard","Dashboard"},
                    {"Comercial",  "pedidos","Pedidos",      "clientes","Clientes",   "faturas","Faturas"},
                    {"Produção",   "receitas","Receitas",     "lotes","Lotes",        "producao","Etapas de Produção", "ingredientes","Ingredientes"},
                    {"Logística",  "veiculos","Veículos"},
                    {"Sistema",    "utilizadores","Utilizadores"},
            };
            case "producao" -> new String[][]{
                    {"Principal", "dashboard","Dashboard"},
                    {"Produção",  "lotes","Lotes", "producao","Etapas de Produção", "receitas","Receitas", "ingredientes","Ingredientes"},
                    {"Pedidos",   "pedidos","Pedidos"},
            };
            case "operador" -> new String[][]{
                    {"Principal",      "dashboard","Dashboard"},
                    {"O Meu Trabalho", "producao","Etapas de Produção", "lotes","Lotes"},
            };
            case "qualidade" -> new String[][]{
                    {"Principal",  "dashboard","Dashboard"},
                    {"Qualidade",  "qualidade","Testes de Qualidade", "lotes","Lotes"},
            };
            case "armazem" -> new String[][]{
                    {"Principal", "dashboard","Dashboard"},
                    {"Armazém",   "ingredientes","Ingredientes", "stock","Movimentações de Stock"},
            };
            case "comercial" -> new String[][]{
                    {"Principal",  "dashboard","Dashboard"},
                    {"Comercial",  "pedidos","Pedidos", "clientes","Clientes", "faturas","Faturas"},
            };
            default -> new String[][]{{"Principal","dashboard","Dashboard"}};
        };
    }
}