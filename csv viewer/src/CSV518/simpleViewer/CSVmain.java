
package CSV518.simpleViewer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;

public class CSVmain{
	public static String[] columnNames = {
                    "Name",
                    "surname",
                    "mail",
                    "phone"
        };
	public static String[][] data = {
                    {"Name1", "surname1", "mail1", "phone1"},
                    {"Name2", "surname2", "mail2", "phone2"},
                    {"Name3", "surname3", "mail3", "phone3"},
        };
		static JTable tab = new JTable(data, columnNames);
		static JScrollPane scrollPane;
		static JFrame frame = new JFrame("example");
		static JButton selectFile;
		static JButton saveFile;
	public static void main(String[] args){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
           
        
		tab = new JTable(new DefaultTableModel(data, columnNames));
		scrollPane = new JScrollPane(tab);
        scrollPane.setBounds(10, 50, 770, 300);
		
		selectFile = new JButton("select file");
		selectFile.setBounds(10,10,130,30);
		selectFile.addActionListener(e -> importCSV());
		
		saveFile = new JButton("save file");
		saveFile.setBounds(180,10,300,30);
		saveFile.addActionListener(e -> exportCSV());
		
		frame.setSize(800,400);
		frame.setLayout(null);
		frame.add(selectFile);
		frame.add(saveFile);
		frame.add(scrollPane);
		
		frame.setVisible(true);
	}
	static void importCSV(){
		System.out.println("importing file...");
		JFileChooser fc = new JFileChooser();
		int fcStatus = fc.showOpenDialog(null); // select file to open
		if (fcStatus == JFileChooser.APPROVE_OPTION){
			File file = new File(fc.getSelectedFile().getAbsolutePath());
			
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line = br.readLine();
				String[] col = null;
				if (line != null){
					col = line.split(";");
				}
				List<String[]> listData = new ArrayList<String[]>();
				
				line = br.readLine();
				while (line != null){
					listData.add(line.split(";"));
					line = br.readLine();
				}
				String [][] tmpData = new String[listData.size()][];
				listData.toArray(tmpData);
				for (int i = 0; i < listData.size(); ++i){
					tmpData[i] = listData.get(i);
				}
				if (col != null){
					columnNames = col;
					data = tmpData;
					
					DefaultTableModel tModel = (DefaultTableModel)tab.getModel();
					tModel.setDataVector(data, columnNames); 
					tab.setModel(tModel);
				
					
					System.out.println("file imported.");
			
				}
				
			} catch (FileNotFoundException e){
				System.out.println("file import failed.");
				System.out.println(e.getMessage());
			} catch (IOException e){
				System.out.println("file import failed.");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("file import canceled.");
			// do nothing
		}
	}
	
	static void exportCSV(){
		{
			DefaultTableModel tModel = (DefaultTableModel)tab.getModel();
			String temp;
			int colCount = tModel.getColumnCount();
			String[] columns = new String[colCount];
			for(int i = 0; i < colCount; ++i){
				columns[i] = tModel.getColumnName(i);
			}
			int rowCount = tModel.getRowCount();
			String[][] tmpData = new String [rowCount][colCount];
			for(int i = 0; i < rowCount; ++i){
				for (int j = 0; j < colCount; ++j){
					tmpData[i][j] = (String)tModel.getValueAt(i, j);
				}
			}
			columnNames = columns;
			data = tmpData;
		}
		
		
	//	String[] col; String[][] data
		JFileChooser fc = new JFileChooser();
		int fcStatus = fc.showSaveDialog(null); // save as our file
		if (fcStatus == JFileChooser.APPROVE_OPTION){
			System.out.println("Saving file...");
			try(FileWriter writer = new FileWriter(fc.getSelectedFile().getAbsolutePath(), false)) //"table.csv"
			{
				String text = "";
				int colsCount = columnNames.length;
				for (int i = 0; i < colsCount; ++i){
					text = text + columnNames[i] + ";";
				}
				
				writer.write(text);
	 
				for (int i = 0; i < data.length; ++i){
					String[] oneRow = data[i];
					text = "";
					for (int j = 0; j < oneRow.length; ++j){
						text = text + oneRow[j] + ";";
					}
					writer.append('\n');
					writer.write(text);
				}
			
				writer.flush();
				System.out.println("file saved succesfully");
			} catch(IOException e){
				System.out.println("file saving failed.");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("file saving canced");
			// do nothing
		}
		
	}
}
