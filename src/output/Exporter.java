package output;

import IntermediateCodeGenerator.CodeGenerator;

import javax.swing.*;
import java.net.URI;
import java.util.LinkedList;

public class Exporter {

    public static void BuildExport(CodeGenerator intermidiate){

        StringBuilder sb = new StringBuilder("http://interplanetary.xyz/kareldisplayer/?");

        String map = JOptionPane.showInputDialog(null,"Insert the map (36 numbers)");
        sb.append("map="+map);

        String position = JOptionPane.showInputDialog(null,"Insert the position");
        sb.append("&position="+position);


        LinkedList<Integer> code = intermidiate.getIntermediateCode();
        sb.append("&size="+code.size());

        for (int i = 0; i < code.size();i++){
            sb.append("&"+i+"="+code.get(i));
        }

        System.out.printf(sb.toString());


       URI displaySite = null;
        try {
            displaySite = new URI(sb.toString());
            java.awt.Desktop.getDesktop().browse(displaySite);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
