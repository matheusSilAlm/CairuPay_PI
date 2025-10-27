/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitarios;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.io.IOException;
/**
 *
 * @author teu_s
 */
public class ImagemUtil{
    
   public static ImageIcon redimensionar (String caminho, int largura, int altura) {
       try {
            // Usa o carregador de classe para encontrar a imagem.
            // O caminho deve ser relativo ao seu pacote de imagens (ex: "/imagens/logo.png")
            Image imagem = new ImageIcon(ImagemUtil.class.getResource(caminho)).getImage();
            
            // Redimensiona a imagem
            Image imagemRedimensionada = imagem.getScaledInstance(
                largura, 
                altura, 
                java.awt.Image.SCALE_SMOOTH
            );
            
            // Retorna o novo ImageIcon
            return new ImageIcon(imagemRedimensionada);
        } catch (Exception e) {
            // Em caso de erro (ex: imagem não encontrada)
            System.err.println("Erro ao carregar ou redimensionar o ícone: " + e.getMessage());
            e.printStackTrace(); // Imprime o rastreamento do erro
            return null; 
        }
    }
}
