import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class MapGenerator{
public MapGenerator() throws IOException {
	String[] str = new String[20];
	str[0] = "XXXXXXXXXXXXXXXXXXXC\n";
	str[19] = "SXXXXXXXXXHXXXXXXXXX\n";
	str[18] = "XXXXXXXXXXXXXXXXXXXX\n";
	str[17] = "XXXXXXXXXXXXXXXXXXXX\n";
	int k = 0;
	for(int i = 1; i < 17; i++ ) {
		str[i] = "";
		for(k = 0; k < 20; k++)
			{
				if(Math.random() < 0.1) 
				{
					if (k != 0 && k != 20){
						if(str[i-1].charAt(k-1) != 'M' && str[i-1].charAt(k+1) != 'M')
						{
							str[i] += "M";
						}
						else {
							str[i] += "X";
						}
					}
					else {
						str[i] += "X";
					}
				}
				else 
				{
					str[i] += "X";
				}
			}
		str[i] += "\n";
	}	
	BufferedWriter writer = new BufferedWriter(new FileWriter("./map/level_map.txt"));
	for(int j = 0;j < 20;j++) {
		writer.write(str[j]);
	}
	writer.close();
}
}