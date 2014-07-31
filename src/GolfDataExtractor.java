import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GolfDataExtractor {

	public static ArrayList<Record> _recordListActual = new ArrayList<Record>();
	public static ArrayList<Record> _recordListAdjusted = new ArrayList<Record>();
	public static ArrayList<Record> _recordListEarnings = new ArrayList<Record>();
	public static ArrayList<Record> _recordListBeforeCut = new ArrayList<Record>();
	public static ArrayList<ArrayList<Record>> _sortedListOfCareers = new ArrayList<ArrayList<Record>>();
	
	public static int[] _histogramPGAActualComplete = new int[1000];
	public static int[] _histogramNationwideActualComplete = new int[1000];
	public static int[] _histogramPGAAdjustedComplete = new int[1000];
	public static int[] _histogramNationwideAdjustedComplete = new int[1000];
	public static int[] _histogramPGABeforeCutComplete = new int[1000];
	public static int[] _histogramNationwideBeforeCutComplete = new int[1000];
	public static int[] _histogramPGAAfterCutComplete = new int[1000];
	public static int[] _histogramNationwideAfterCutComplete = new int[1000];
	
	public static ArrayList<int[]> _histogramPGAActualYearly = new ArrayList<int[]>();
	public static ArrayList<int[]> _histogramNationwideActualYearly = new ArrayList<int[]>();
	public static ArrayList<int[]> _histogramPGAAdjustedYearly = new ArrayList<int[]>();
	public static ArrayList<int[]> _histogramNationwideAdjustedYearly = new ArrayList<int[]>();
	public static ArrayList<int[]> _histogramPGABeforeCutYearly = new ArrayList<int[]>();
	public static ArrayList<int[]> _histogramNationwideBeforeCutYearly = new ArrayList<int[]>();
	public static ArrayList<int[]> _histogramPGAAfterCutYearly = new ArrayList<int[]>();
	public static ArrayList<int[]> _histogramNationwideAfterCutYearly = new ArrayList<int[]>();
	
	public static void convertToRecordsActual(){
		try{
			// Read the PGA file
			File PGAfile = new File("PGA Tour Scoring Average Raw Data First - 1980-2010-Actual.csv");
			BufferedReader PGAreader = new BufferedReader(new FileReader(PGAfile));
			String text = null;
			String tour = "PGA";
			int year = 0;
			int[] histogramPGAActualYearly = new int[1000];
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = PGAreader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					_histogramPGAActualYearly.add(histogramPGAActualYearly);
					year = Integer.parseInt(split[0].substring(1));
					// Create a new histogram array object for each year to hold histogram information for that year
					histogramPGAActualYearly = new int[1000];
				}
				else{
					if(!"".equals(split[2])){
						//new Record(String playerName, int year, String tour, double averageActualStrokes, String averageActualStrokesRank, int totalStrokes, int rounds)
						_recordListActual.add(new Record(split[2], year, tour, Double.parseDouble(split[3]), split[0], Integer.parseInt(split[4]), Integer.parseInt(split[5])));
						
						// Add score information for histogram generation
						_histogramPGAActualComplete[(int)Math.round(Double.parseDouble(split[3])*10)]++;
						histogramPGAActualYearly[(int)Math.round(Double.parseDouble(split[3])*10)]++;
					}
				}
			}
			// Add the last year of histogram data into the histogram list, remove the first one as the first one is placeholder blank
			_histogramPGAActualYearly.add(histogramPGAActualYearly);
			_histogramPGAActualYearly.remove(0);
			
			// Read the Nationwide file
			File Nationwidefile = new File("Nationwide Tour Scoring Average Raw Data First - 1990-2010-Actual.csv");
			BufferedReader Nationwidereader = new BufferedReader(new FileReader(Nationwidefile));
			text = null;
			tour = "Nationwide";
			year = 0;
			int[] histogramNationwideActualYearly = new int[1000];
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = Nationwidereader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					_histogramNationwideActualYearly.add(histogramNationwideActualYearly);
					year = Integer.parseInt(split[0].substring(1));
					// Create a new histogram array object for each year to hold histogram information for that year
					histogramNationwideActualYearly = new int[1000];
				}
				else{
					if(!"".equals(split[2])){
						//new Record(String playerName, int year, String tour, double averageActualStrokes, String averageActualStrokesRank, int totalStrokes, int rounds)
						if((double)Integer.parseInt(split[5]) / (double)Integer.parseInt(split[3]) < Double.parseDouble(split[4]) - 1 
								|| (double)Integer.parseInt(split[5]) / (double)Integer.parseInt(split[3]) > Double.parseDouble(split[4]) + 1)
							_recordListActual.add(new Record(split[2], year, tour, Double.parseDouble(split[4]), split[0], Integer.parseInt(split[5]), Integer.parseInt(split[6])));
						else
							_recordListActual.add(new Record(split[2], year, tour, Double.parseDouble(split[4]), split[0], Integer.parseInt(split[5]), Integer.parseInt(split[3])));
						
						// Add score information for histogram generation
						_histogramNationwideActualComplete[(int)Math.round(Double.parseDouble(split[4])*10)]++;
						histogramNationwideActualYearly[(int)Math.round(Double.parseDouble(split[4])*10)]++;
					}
				}
			}
			// Add the last year of histogram data into the histogram list, remove the first one as the first one is placeholder blank
			_histogramNationwideActualYearly.add(histogramNationwideActualYearly);
			_histogramNationwideActualYearly.remove(0);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void convertToRecordsAdjusted(){
		try{
			// Read the PGA file
			File PGAfile = new File("PGA Tour Scoring Average Raw Data First - 1980-2010-Adjusted.csv");
			BufferedReader PGAreader = new BufferedReader(new FileReader(PGAfile));
			String text = null;
			String tour = "PGA";
			int year = 0;
			int[] histogramPGAAdjustedYearly = new int[1000];
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = PGAreader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					_histogramPGAAdjustedYearly.add(histogramPGAAdjustedYearly);
					year = Integer.parseInt(split[0].substring(1));
					// Create a new histogram array object for each year to hold histogram information for that year
					histogramPGAAdjustedYearly = new int[1000];
				}
				else{
					//new Record(String playerName, int year, String tour, double averageAdjustedStrokes, String averageAdjustedStrokesRank, int totalStrokes, int rounds)
//					_recordListAdjusted.add(new Record(split[2], year, tour, Double.parseDouble(split[3]), split[0], Integer.parseInt(split[4]), Integer.parseInt(split[6]), true));
					
					// Find the associated record for actual scores and attach adjusted score information onto that record
					for(int i = 0; i < _recordListActual.size(); i++){
						if(_recordListActual.get(i)._year == year && _recordListActual.get(i)._playerName.equals(split[2]) && _recordListActual.get(i)._tour.equals(tour)){
							_recordListActual.get(i)._averageAdjustedStrokes = Double.parseDouble(split[3]);
							_recordListActual.get(i)._averageAdjustedStrokesRank = split[0];
							break;
						}
					}
					// Add score information for histogram generation
					_histogramPGAAdjustedComplete[(int)Math.round(Double.parseDouble(split[3])*10)]++;
					histogramPGAAdjustedYearly[(int)Math.round(Double.parseDouble(split[3])*10)]++;
				}
			}
			// Add the last year of histogram data into the histogram list, remove the first one as the first one is placeholder blank
			_histogramPGAAdjustedYearly.add(histogramPGAAdjustedYearly);
			_histogramPGAAdjustedYearly.remove(0);
			
			// Read the Nationwide file
			File Nationwidefile = new File("Nationwide Tour Scoring Average Raw Data First - 1990-2010-SDFAverage.csv");
			BufferedReader Nationwidereader = new BufferedReader(new FileReader(Nationwidefile));
			text = null;
			tour = "Nationwide";
			year = 0;
			int[] histogramNationwideAdjustedYearly = new int[1000];
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = Nationwidereader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					_histogramNationwideAdjustedYearly.add(histogramNationwideAdjustedYearly);
					year = Integer.parseInt(split[0].substring(1));
					// Create a new histogram array object for each year to hold histogram information for that year
					histogramNationwideAdjustedYearly = new int[1000];
				}
				else{
					//new Record(String playerName, int year, String tour, double averageAdjustedStrokes, String averageAdjustedStrokesRank, int totalStrokes, int rounds)
					/**
					double averageAdjustedStrokes = 71.35 - Double.parseDouble(split[6]) + Double.parseDouble(split[5]);
					**/
//					_recordListAdjusted.add(new Record(split[2], year, tour, 71.35 - Double.parseDouble(split[6]) + Double.parseDouble(split[5]), split[0], 0, 0, true));
					
					// Find the associated record for actual scores and attach adjusted score information onto that record
					for(int i = 0; i < _recordListActual.size(); i++){
						if(_recordListActual.get(i)._year == year && _recordListActual.get(i)._playerName.equals(split[2]) && _recordListActual.get(i)._tour.equals(tour)){
							_recordListActual.get(i)._averageAdjustedStrokes = 71.35 - Double.parseDouble(split[6]) + Double.parseDouble(split[5]);
							_recordListActual.get(i)._averageAdjustedStrokesRank = split[0];
							break;
						}
					}
					// Add score information for histogram generation
					_histogramNationwideAdjustedComplete[(int)Math.round((71.35 - Double.parseDouble(split[6]) + Double.parseDouble(split[5]))*10)]++;
					histogramNationwideAdjustedYearly[(int)Math.round((71.35 - Double.parseDouble(split[6]) + Double.parseDouble(split[5]))*10)]++;
				}
			}
			// Add the last year of histogram data into the histogram list, remove the first one as the first one is placeholder blank
			_histogramNationwideAdjustedYearly.add(histogramNationwideAdjustedYearly);
			_histogramNationwideAdjustedYearly.remove(0);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void convertToRecordsMoneyList(){
		try{
			// Read the PGA file
			File PGAfile = new File("PGA Tour End of Year Money List - 1980-2010.csv");
			BufferedReader PGAreader = new BufferedReader(new FileReader(PGAfile));
			String text = null;
			String tour = "PGA";
			int year = 0;
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = PGAreader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					year = Integer.parseInt(split[0].substring(1));
				}
				else{
					//new Record(String playerName, int year, String tour, int eventsPlayed, int earnings, String earningsRank)
//					_recordListEarnings.add(new Record(split[2], year, tour, Integer.parseInt(split[3]), Integer.parseInt(split[4]), split[0]));
					
					// Find the associated record for actual scores and attach adjusted score information onto that record
					for(int i = 0; i < _recordListActual.size(); i++){
						if(_recordListActual.get(i)._year == year && _recordListActual.get(i)._playerName.equals(split[2]) && _recordListActual.get(i)._tour.equals(tour)){
							_recordListActual.get(i)._eventsPlayed = Integer.parseInt(split[3]);
							_recordListActual.get(i)._earnings = Integer.parseInt(split[4]);
							_recordListActual.get(i)._earningsRank = split[0];
							break;
						}
					}
				}
			}
			
			// Read the Nationwide file
			File Nationwidefile = new File("Nationwide Tour End of Year Money List - 1990-2010.csv");
			BufferedReader Nationwidereader = new BufferedReader(new FileReader(Nationwidefile));
			text = null;
			tour = "Nationwide";
			year = 0;
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = Nationwidereader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					year = Integer.parseInt(split[0].substring(1));
				}
				else{
					//new Record(String playerName, int year, String tour, int eventsPlayed, int earnings, String earningsRank)
//					_recordListEarnings.add(new Record(split[2], year, tour, Integer.parseInt(split[3]), Integer.parseInt(split[4]), split[0]));
					
					// Find the associated record for actual scores and attach adjusted score information onto that record
					for(int i = 0; i < _recordListActual.size(); i++){
						if(_recordListActual.get(i)._year == year && _recordListActual.get(i)._playerName.equals(split[2]) && _recordListActual.get(i)._tour.equals(tour)){
							_recordListActual.get(i)._eventsPlayed = Integer.parseInt(split[3]);
							_recordListActual.get(i)._earnings = Integer.parseInt(split[4]);
							_recordListActual.get(i)._earningsRank = split[0];
							
							break;
						}
					}
				}
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void convertToRecordsBeforeCut(){
		try{
			// Read the PGA file
			File PGAfile = new File("PGA Tour Scoring Average Raw Data First - 1980-2010-BeforeCutActual.csv");
			BufferedReader PGAreader = new BufferedReader(new FileReader(PGAfile));
			String text = null;
			String tour = "PGA";
			int year = 0;
			int[] histogramPGABeforeCutYearly = new int[1000];
			int[] histogramPGAAfterCutYearly = new int[1000];
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = PGAreader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					_histogramPGABeforeCutYearly.add(histogramPGABeforeCutYearly);
					_histogramPGAAfterCutYearly.add(histogramPGAAfterCutYearly);
					year = Integer.parseInt(split[0].substring(1));
					// Create a new histogram array object for each year to hold histogram information for that year
					histogramPGABeforeCutYearly = new int[1000];
					histogramPGAAfterCutYearly = new int[1000];
				}
				else{
					//new Record(String playerName, int year, String tour, double averageBeforeCutStrokes, String averageBeforeCutRank, int totalStrokesBeforeCut, int rounds, boolean adjusted, boolean beforeCut)
//					_recordListBeforeCut.add(new Record(split[2], year, tour, Double.parseDouble(split[3]), split[0], Integer.parseInt(split[4]), Integer.parseInt(split[5]), false, true));
					
					double averageAfterCutStrokes = 0;
					// Find the associated record for actual scores and attach adjusted score information onto that record
					for(int i = 0; i < _recordListActual.size(); i++){
						if(year == _recordListActual.get(i)._year && _recordListActual.get(i)._playerName.equals(split[2]) && _recordListActual.get(i)._tour.equals(tour)){
							_recordListActual.get(i)._averageBeforeCutStrokes = Double.parseDouble(split[3]);
							_recordListActual.get(i)._averageBeforeCutRank = split[0];
							_recordListActual.get(i)._totalStrokesBeforeCut = Integer.parseInt(split[4]);
							_recordListActual.get(i)._totalBeforeCutRounds = Integer.parseInt(split[5]);
							
							_recordListActual.get(i)._totalStrokesAfterCut = _recordListActual.get(i)._totalStrokes - Integer.parseInt(split[4]);
							_recordListActual.get(i)._totalAfterCutRounds = _recordListActual.get(i)._rounds  - Integer.parseInt(split[5]);
							if(_recordListActual.get(i)._totalAfterCutRounds > 0){
								averageAfterCutStrokes = (double)_recordListActual.get(i)._totalStrokesAfterCut  / _recordListActual.get(i)._totalAfterCutRounds;
								_recordListActual.get(i)._averageAfterCutStrokes = averageAfterCutStrokes;
							}
							break;
						}
					}
					
					// Add score information for histogram generation
					_histogramPGABeforeCutComplete[(int)Math.round(Double.parseDouble(split[3])*10)]++;
					histogramPGABeforeCutYearly[(int)Math.round(Double.parseDouble(split[3])*10)]++;
					if(averageAfterCutStrokes > 0){
						_histogramPGAAfterCutComplete[(int)Math.round(averageAfterCutStrokes*10)]++;
						histogramPGAAfterCutYearly[(int)Math.round(averageAfterCutStrokes*10)]++;
					}
				}
			}
			// Add the last year of histogram data into the histogram list, remove the first one as the first one is placeholder blank
			_histogramPGABeforeCutYearly.add(histogramPGABeforeCutYearly);
			_histogramPGAAfterCutYearly.add(histogramPGAAfterCutYearly);
			_histogramPGABeforeCutYearly.remove(0);
			_histogramPGAAfterCutYearly.remove(0);
			
			// Read the Nationwide file
			File Nationwidefile = new File("Nationwide Tour Scoring Average Raw Data First - 1990-2010-BeforeCutActual.csv");
			BufferedReader Nationwidereader = new BufferedReader(new FileReader(Nationwidefile));
			text = null;
			tour = "Nationwide";
			year = 0;
			int[] histogramNationwideBeforeCutYearly = new int[1000];
			int[] histogramNationwideAfterCutYearly = new int[1000];
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = Nationwidereader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
					_histogramNationwideBeforeCutYearly.add(histogramNationwideBeforeCutYearly);
					_histogramNationwideAfterCutYearly.add(histogramNationwideAfterCutYearly);
					year = Integer.parseInt(split[0].substring(1));
					// Create a new histogram array object for each year to hold histogram information for that year
					histogramNationwideBeforeCutYearly = new int[1000];
					histogramNationwideAfterCutYearly = new int[1000];
				}
				else{
					//new Record(String playerName, int year, String tour, double averageBeforeCutStrokes, String averageBeforeCutRank, int totalStrokesBeforeCut, int rounds, boolean adjusted, boolean beforeCut)
//					_recordListBeforeCut.add(new Record(split[2], year, tour, Double.parseDouble(split[3]), split[0], Integer.parseInt(split[4]), Integer.parseInt(split[5]), false, true));
					
					// Find the associated record for actual scores and attach adjusted score information onto that record
					double averageAfterCutStrokes = 0;
					for(int i = 0; i < _recordListActual.size(); i++){
						if(year == _recordListActual.get(i)._year && _recordListActual.get(i)._playerName.equals(split[2]) && _recordListActual.get(i)._tour.equals(tour)){
							_recordListActual.get(i)._averageBeforeCutStrokes = Double.parseDouble(split[3]);
							_recordListActual.get(i)._averageBeforeCutRank = split[0];
							_recordListActual.get(i)._totalStrokesBeforeCut = Integer.parseInt(split[4]);
							_recordListActual.get(i)._totalBeforeCutRounds = Integer.parseInt(split[5]);
							
							_recordListActual.get(i)._totalStrokesAfterCut = _recordListActual.get(i)._totalStrokes - Integer.parseInt(split[4]);
							_recordListActual.get(i)._totalAfterCutRounds = _recordListActual.get(i)._rounds  - Integer.parseInt(split[5]);
							if(_recordListActual.get(i)._totalAfterCutRounds > 0){
								averageAfterCutStrokes = (double)_recordListActual.get(i)._totalStrokesAfterCut  / _recordListActual.get(i)._totalAfterCutRounds;
								_recordListActual.get(i)._averageAfterCutStrokes = averageAfterCutStrokes;
							}
							break;
						}
					}
					
					// Add score information for histogram generation
					_histogramNationwideBeforeCutComplete[(int)Math.round(Double.parseDouble(split[3])*10)]++;
					histogramNationwideBeforeCutYearly[(int)Math.round(Double.parseDouble(split[3])*10)]++;
					if(averageAfterCutStrokes > 0){
						_histogramNationwideAfterCutComplete[(int)Math.round(averageAfterCutStrokes*10)]++;
						histogramNationwideAfterCutYearly[(int)Math.round(averageAfterCutStrokes*10)]++;
					}
				}
			}
			// Add the last year of histogram data into the histogram list, remove the first one as the first one is placeholder blank
			_histogramNationwideBeforeCutYearly.add(histogramNationwideBeforeCutYearly);
			_histogramNationwideAfterCutYearly.add(histogramNationwideAfterCutYearly);
			_histogramNationwideBeforeCutYearly.remove(0);
			_histogramNationwideAfterCutYearly.remove(0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void convertToRecordsAge(){
		try{
			// Read the PGA file
			File PGAfile = new File("PGA Tour Player Age - 2003-2010.csv");
			BufferedReader PGAreader = new BufferedReader(new FileReader(PGAfile));
			String text = null;
			String tour = "PGA";
			int year = 0;
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = PGAreader.readLine())){
				String[] split = text.split(",");
				// If the line has only one split element, then that line is the year
				// Otherwise the line is a record which needs to be inserted into the record list
				if(split.length <= 2){
//					year = Integer.parseInt(split[0].substring(1));
					// Do nothing as it turns out the age data for all years is equal to current age
					year = 2010;
				}
				else if("AGE".equals(split[2]) || !Character.isDigit(split[2].charAt(0))){
					// Do nothing and just skip line
				}
				else{
					// Find the associated record for actual scores and attach adjusted score information onto that record
					for(int i = 0; i < _recordListActual.size(); i++){
						if(_recordListActual.get(i)._playerName.equals(split[1]) && _recordListActual.get(i)._tour.equals(tour)){
							for(int j = 0; j < _recordListActual.size(); j++){
								if(_recordListActual.get(j)._age == 0 && _recordListActual.get(j)._playerName.equals(split[1])){
									_recordListActual.get(j)._age = Integer.parseInt(split[2]) - (year - _recordListActual.get(j)._year);

								}
							}
							break;
						}
					}
				}
			}
			
			// Read the PGA file
			File file = new File("Nationwide entry ages - more input.csv");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			text = null;
			year = 2010;
			// Read one line at a time and create a record from that line to insert into the record list
			while(null != (text = reader.readLine())){
				String[] split = text.split(",");
				// Find the associated record for actual scores and attach adjusted score information onto that record
				for(int i = 0; i < _recordListActual.size(); i++){
					if(_recordListActual.get(i)._playerName.equals(split[0])){
						for(int j = 0; j < _recordListActual.size(); j++){
							if(_recordListActual.get(j)._age == 0 && _recordListActual.get(j)._playerName.equals(split[0])){
								_recordListActual.get(j)._age = (year - Integer.parseInt(split[2])) - (year - _recordListActual.get(j)._year);
							}
						}
						break;
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void joinRecords(){
//		for(int i = 0; i < _recordListActual.size(); i++){
//			// Join actual and adjusted stroke records
//			for(int j = 0; j < _recordListAdjusted.size(); j++){
//				if(_recordListActual.get(i)._playerName.equals(_recordListAdjusted.get(j)._playerName)
//						&& _recordListActual.get(i)._year == _recordListAdjusted.get(j)._year
//						&& _recordListActual.get(i)._tour.equals(_recordListAdjusted.get(j)._tour)){
//					_recordListActual.get(i)._averageAdjustedStrokes = _recordListAdjusted.get(j)._averageAdjustedStrokes;
//					_recordListActual.get(i)._averageAdjustedStrokesRank = _recordListAdjusted.get(j)._averageAdjustedStrokesRank;
//					break;
//				}
//			}
//			
//			// Join strokes and money records
//			for(int j = 0; j < _recordListEarnings.size(); j++){
//				if(_recordListActual.get(i)._playerName.equals(_recordListEarnings.get(j)._playerName)
//						&& _recordListActual.get(i)._year == _recordListEarnings.get(j)._year
//						&& _recordListActual.get(i)._tour.equals(_recordListEarnings.get(j)._tour)){
//					_recordListActual.get(i)._eventsPlayed = _recordListEarnings.get(j)._eventsPlayed;
//					_recordListActual.get(i)._earnings = _recordListEarnings.get(j)._earnings;
//					_recordListActual.get(i)._earningsRank = _recordListEarnings.get(j)._earningsRank;
//					break;
//				}
//			}
//			
//			// Join total and before cut stroke records
//			for(int j = 0; j < _recordListBeforeCut.size(); j++){
//				if(_recordListActual.get(i)._playerName.equals(_recordListBeforeCut.get(j)._playerName)
//						&& _recordListActual.get(i)._year == _recordListBeforeCut.get(j)._year
//						&& _recordListActual.get(i)._tour.equals(_recordListBeforeCut.get(j)._tour)){
//					_recordListActual.get(i)._averageBeforeCutStrokes = _recordListBeforeCut.get(j)._averageBeforeCutStrokes;
//					_recordListActual.get(i)._averageBeforeCutRank = _recordListBeforeCut.get(j)._averageBeforeCutRank;
//					_recordListActual.get(i)._totalStrokesBeforeCut = _recordListBeforeCut.get(j)._totalStrokesBeforeCut;
//					_recordListActual.get(i)._totalBeforeCutRounds = _recordListBeforeCut.get(j)._totalBeforeCutRounds;
//					
//					// Calculate rounds, strokes, and average stroke for after cut records
//					_recordListActual.get(i)._totalStrokesAfterCut = _recordListActual.get(i)._totalStrokes - _recordListActual.get(i)._totalStrokesBeforeCut;
//					_recordListActual.get(i)._totalAfterCutRounds = _recordListActual.get(i)._rounds  - _recordListActual.get(i)._totalBeforeCutRounds;
//					if(_recordListActual.get(i)._totalAfterCutRounds > 0)
//						_recordListActual.get(i)._averageAfterCutStrokes = (double)_recordListActual.get(i)._totalStrokesAfterCut  / _recordListActual.get(i)._totalAfterCutRounds;
//					break;
//				}
//			}
//		}
	}
	
	public static void sortRecordsByNames(){
		// Create names list so that only new players are inserted
		ArrayList<String> insertedNames = new ArrayList<String>();
		// Iterate through all records, if the player data has not already been inserted, insert all data for that player
		for(int i = 0; i < _recordListActual.size(); i++){
			if(!insertedNames.contains(_recordListActual.get(i)._playerName)){
				insertedNames.add(_recordListActual.get(i)._playerName);
				ArrayList<Record> playerCareer = new ArrayList<Record>();
				// Insert all career data for the given player
				for(int j = i; j < _recordListActual.size(); j++){
					if(_recordListActual.get(i)._playerName.equals(_recordListActual.get(j)._playerName)){
						playerCareer.add(_recordListActual.get(j));
					}
				}
				_sortedListOfCareers.add(playerCareer);
			}
		}
	}
	
	public static void sortPlayerCareerByTime(){
		for(int i = 0; i < _sortedListOfCareers.size(); i++){
			// Extract a single player career
			ArrayList<Record> playerCareer = _sortedListOfCareers.get(i);
			// Sort that player career by time from earliest to most recent
			for(int j = 0; j < playerCareer.size(); j++){
				int k = j;
				Record B = playerCareer.get(j);
				while ((k > 0) && (playerCareer.get(k-1)._year > B._year)){
					playerCareer.set(k, playerCareer.get(k-1));
					k--;
				}
				playerCareer.set(k, B);
			}
		}
	}
	
	public static void roundTwoDecimals(){
		for(int i = 0; i < _sortedListOfCareers.size(); i++){
			ArrayList<Record> playerCareer = _sortedListOfCareers.get(i);
			for(int j = 0; j < playerCareer.size(); j++){
				playerCareer.get(j).roundTwoDecimals();
			}
		}
	}
	
	public static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
	
	public static double roundOneDecimal(double d) {
		DecimalFormat oneDForm = new DecimalFormat("#.#");
		return Double.valueOf(oneDForm.format(d));
	}
	
	public static void outputPlayerCareers(){
		StringBuffer resultBuffer = new StringBuffer();
		StringBuffer tempNationwideScoresBuffer = new StringBuffer();
		for(int i = 0; i < _sortedListOfCareers.size(); i++){
			ArrayList<Record> playerCareer = _sortedListOfCareers.get(i);
			resultBuffer.append("Player," + playerCareer.get(0)._playerName + "\n");
			
			resultBuffer.append("Year,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._year + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Age,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._age + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Average actual strokes,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._averageActualStrokes + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Average actual strokes rank,");
			for(int j = 0; j < playerCareer.size(); j++){
				if('T' == playerCareer.get(j)._averageActualStrokesRank.charAt(0)){
					resultBuffer.append(playerCareer.get(j)._averageActualStrokesRank.substring(1) + ",");
				}
				else
					resultBuffer.append(playerCareer.get(j)._averageActualStrokesRank + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Average adjusted strokes,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._averageAdjustedStrokes + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Average adjusted strokes rank,");
			for(int j = 0; j < playerCareer.size(); j++){
				if(playerCareer.get(j)._averageAdjustedStrokesRank.length() > 0){
					if('T' == playerCareer.get(j)._averageAdjustedStrokesRank.charAt(0)){
						resultBuffer.append(playerCareer.get(j)._averageAdjustedStrokesRank.substring(1) + ",");
					}
					else
						resultBuffer.append(playerCareer.get(j)._averageAdjustedStrokesRank + ",");
				}
				else{
					resultBuffer.append(",");
				}
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Rounds played,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._rounds + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Average strokes before cut,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._averageBeforeCutStrokes + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Average strokes before cut rank,");
			for(int j = 0; j < playerCareer.size(); j++){
				if(playerCareer.get(j)._averageBeforeCutRank.length() > 0){
					if('T' == playerCareer.get(j)._averageBeforeCutRank.charAt(0)){
						resultBuffer.append(playerCareer.get(j)._averageBeforeCutRank.substring(1) + ",");
					}
					else
						resultBuffer.append(playerCareer.get(j)._averageBeforeCutRank + ",");
				}
				else{
					resultBuffer.append(",");
				}
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Rounds played before cut,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._totalBeforeCutRounds + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Average strokes after cut,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._averageAfterCutStrokes + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Rounds played after cut,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._totalAfterCutRounds + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Events played,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._eventsPlayed + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Money,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._earnings + ",");
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Money rank,");
			for(int j = 0; j < playerCareer.size(); j++){
				if(playerCareer.get(j)._earningsRank.length() > 0){
					if('T' == playerCareer.get(j)._earningsRank.charAt(0))
						resultBuffer.append(playerCareer.get(j)._earningsRank.substring(1) + ",");
					else
						resultBuffer.append(playerCareer.get(j)._earningsRank + ",");
				}
			}
			resultBuffer.append("\n");
			
			resultBuffer.append("Tour,");
			for(int j = 0; j < playerCareer.size(); j++){
				resultBuffer.append(playerCareer.get(j)._tour + ",");
			}
			resultBuffer.append("\n");
			resultBuffer.append("\n");
			
			for(int j = 0; j < playerCareer.size(); j++){
				if(playerCareer.get(j)._year > 1997 && "Nationwide".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageAdjustedStrokes > 55){
					tempNationwideScoresBuffer.append(playerCareer.get(j)._averageAdjustedStrokes + "\n");
				}
			}
		}
		
		try{
			FileWriter outFile = new FileWriter("Player Careers.csv");
			PrintWriter out = new PrintWriter(outFile);
			out.print(resultBuffer);
			out.close();
			
			FileWriter testFile = new FileWriter("Nationwide Average Strokes post1997.csv");
			PrintWriter test = new PrintWriter(testFile);
			test.print(tempNationwideScoresBuffer);
			test.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void outputPGAPlayerCareers(){
		StringBuffer resultBuffer = new StringBuffer();
		StringBuffer shortResultBuffer = new StringBuffer();
		StringBuffer namesBuffer = new StringBuffer();
		StringBuffer bestScoreBuffer = new StringBuffer();
		StringBuffer eventsPlayedBuffer = new StringBuffer();
		StringBuffer eventsPlayedByAgePGABuffer = new StringBuffer();
		StringBuffer eventsPlayedByAgeNationwideBuffer = new StringBuffer();
		StringBuffer ranksBuffer = new StringBuffer();

		try{
			File nameFile = new File("Player Careers - Names - Static.csv");
			BufferedReader nameReader = new BufferedReader(new FileReader(nameFile));
			String nameText = null;
			String[][] nameString = new String[140][2];
			int count = 0;
			while((nameText = nameReader.readLine()) != null){
				String[] cut = nameText.split(",");
				nameString[count][0] = cut[0];
				nameString[count][1] = cut[1];
				count++;
			}

			for(int i = 0; i < _sortedListOfCareers.size(); i++){
				ArrayList<Record> playerCareer = _sortedListOfCareers.get(i);			

				if(playerCareer.size() > 15){
					int yearBorn = 0;
					for(int j = 0; j < nameString.length; j++){
						if(nameString[j][0].equals(playerCareer.get(0)._playerName)){
							yearBorn = Integer.parseInt(nameString[j][1]);
						}
					}
					resultBuffer.append("Player Name," + playerCareer.get(0)._playerName + "\n");

//					for(int j = 1; j <= (playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1); j++){
//						resultBuffer.append((j) + ",");
//					}
//					resultBuffer.append("\n");

					String[] playerCareerArray = new String[playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1];
					String[] playerCareerRankArray = new String[playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1];
					String[] playerCareerMoneyRankArray = new String[playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1];
					for(int j = 0; j < playerCareerArray.length; j++){
						playerCareerArray[j] = "";
						playerCareerRankArray[j] = "";
						playerCareerMoneyRankArray[j] = "";
					}
					
					double eventsPlayed = 0.0;
					double totalScore = 0.0;
					int times = 0;
					boolean nationwide = false;
					for(int j = 0; j < playerCareer.size(); j++){
						if(playerCareer.get(j)._eventsPlayed > 0){
							eventsPlayed += playerCareer.get(j)._eventsPlayed;
							times++;
//							eventsPlayedBuffer.append(playerCareer.get(j)._eventsPlayed + ",");
						}
						if("Nationwide".equals(playerCareer.get(j)._tour)){
							nationwide = true;
							eventsPlayedByAgeNationwideBuffer.append(playerCareer.get(j)._eventsPlayed + "," + (j + playerCareer.get(0)._year - yearBorn - 1) + "," + playerCareer.get(j)._averageActualStrokes + "," + "\n");
						}
						else{
							eventsPlayedByAgePGABuffer.append(playerCareer.get(j)._eventsPlayed + "," + (j + playerCareer.get(0)._year - yearBorn - 1) + "," + playerCareer.get(j)._averageActualStrokes + "," + "\n");
						}
						totalScore += playerCareer.get(j)._averageActualStrokes;
					}
					eventsPlayedBuffer.append(nationwide + "," + (totalScore / playerCareer.size()) + "," + (eventsPlayed / times) + "\n");
					
					Double bestScore = 100.0;

					int bestScoreIndex = 0;
					for(int j = 0; j < playerCareer.size(); j++){
						if("PGA".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageActualStrokes > 55 && playerCareer.get(j)._averageActualStrokes < bestScore){
							bestScore = playerCareer.get(j)._averageActualStrokes;
							bestScoreIndex = j;
						}
						else if("Nationwide".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageActualStrokes > 55 && (playerCareer.get(j)._averageActualStrokes + 0.85) < bestScore){
							bestScore = playerCareer.get(j)._averageActualStrokes + 0.85;
							bestScoreIndex = j;
						}
					}
					bestScoreBuffer.append(playerCareer.get(bestScoreIndex)._playerName + "," + (playerCareer.get(bestScoreIndex)._year - yearBorn) + "," + bestScore + "\n");
					
					for(int j = 0; j < playerCareer.size(); j++){
						if(playerCareer.get(j)._averageActualStrokes > 55){
							if("PGA".equals(playerCareer.get(j)._tour)){
								playerCareerArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = Double.toString(playerCareer.get(j)._averageActualStrokes);
								if(playerCareer.get(j)._averageActualStrokesRank.startsWith("T"))
									playerCareerRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._averageActualStrokesRank.substring(1);
								else
									playerCareerRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._averageActualStrokesRank;
								if(playerCareer.get(j)._earningsRank.startsWith("T"))
									playerCareerMoneyRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._earningsRank.substring(1);
								else
									playerCareerMoneyRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._earningsRank;

//							resultBuffer.append(playerCareer.get(j)._averageActualStrokes + ",");
							}
							if("Nationwide".equals(playerCareer.get(j)._tour)){
								playerCareerArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = Double.toString(playerCareer.get(j)._averageActualStrokes + 0.85);
								playerCareerRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = " ";
								playerCareerMoneyRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = " ";
//							resultBuffer.append((playerCareer.get(j)._averageActualStrokes + 0.85) + ",");
//							else{
//							resultBuffer.append(",");
//							}
							}
						}
//						else{
//						resultBuffer.append(",");
//						}
					}
					resultBuffer.append("Year,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j]))
							resultBuffer.append((j + playerCareer.get(0)._year) + ",");
					}
					resultBuffer.append("\n");
					
					resultBuffer.append("Age,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j])){
							resultBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
							shortResultBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
						}
						if(!"".equals(playerCareerRankArray[j]) && !" ".equals(playerCareerRankArray[j])){
							ranksBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					ranksBuffer.append("\n");

					resultBuffer.append("Score,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j])){
							resultBuffer.append(playerCareerArray[j] + ",");
							shortResultBuffer.append(playerCareerArray[j] + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					resultBuffer.append("Score Rank,");
					for(int j = 0; j < playerCareerRankArray.length; j++){
						if(!"".equals(playerCareerRankArray[j])){
							resultBuffer.append(playerCareerRankArray[j] + ",");
							shortResultBuffer.append(playerCareerRankArray[j] + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					resultBuffer.append("Money Rank,");
					for(int j = 0; j < playerCareerMoneyRankArray.length; j++){
						if(!"".equals(playerCareerMoneyRankArray[j])){
							resultBuffer.append(playerCareerMoneyRankArray[j] + ",");
							shortResultBuffer.append(playerCareerMoneyRankArray[j] + ",");
						}
						if(!"".equals(playerCareerMoneyRankArray[j]) && !" ".equals(playerCareerMoneyRankArray[j])){
							ranksBuffer.append(playerCareerMoneyRankArray[j] + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					ranksBuffer.append("\n");

					namesBuffer.append(playerCareer.get(0)._playerName + "\n");
				}
			}
			
			nameFile = new File("Player Careers - Names - 10to15 - Static.csv");
			nameReader = new BufferedReader(new FileReader(nameFile));
			nameText = null;
			nameString = new String[185][2];
			count = 0;
			while((nameText = nameReader.readLine()) != null){
				String[] cut = nameText.split(",");
				nameString[count][0] = cut[0];
				nameString[count][1] = cut[1];
				count++;
			}

			for(int i = 0; i < _sortedListOfCareers.size(); i++){
				ArrayList<Record> playerCareer = _sortedListOfCareers.get(i);			

				if(playerCareer.size() >= 10 && playerCareer.size() <= 15){
					int yearBorn = 0;
					for(int j = 0; j < nameString.length; j++){
						if(nameString[j][0].equals(playerCareer.get(0)._playerName)){
							yearBorn = Integer.parseInt(nameString[j][1]);
						}
					}
					resultBuffer.append("Player Name," + playerCareer.get(0)._playerName + "\n");

//					for(int j = 1; j <= (playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1); j++){
//						resultBuffer.append((j) + ",");
//					}
//					resultBuffer.append("\n");

					String[] playerCareerArray = new String[playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1];
					String[] playerCareerRankArray = new String[playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1];
					String[] playerCareerMoneyRankArray = new String[playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1];
					for(int j = 0; j < playerCareerArray.length; j++){
						playerCareerArray[j] = "";
						playerCareerRankArray[j] = "";
						playerCareerMoneyRankArray[j] = "";
					}
					
					double eventsPlayed = 0.0;
					double totalScore = 0.0;
					int times = 0;
					boolean nationwide = false;
					for(int j = 0; j < playerCareer.size(); j++){
						if(playerCareer.get(j)._eventsPlayed > 0){
							eventsPlayed += playerCareer.get(j)._eventsPlayed;
							times++;
//							eventsPlayedBuffer.append(playerCareer.get(j)._eventsPlayed + ",");
						}
						if("Nationwide".equals(playerCareer.get(j)._tour)){
							nationwide = true;
							eventsPlayedByAgeNationwideBuffer.append(playerCareer.get(j)._eventsPlayed + "," + (j + playerCareer.get(0)._year - yearBorn - 1) + "," + playerCareer.get(j)._averageActualStrokes + "," + "\n");
						}
						else{
							eventsPlayedByAgePGABuffer.append(playerCareer.get(j)._eventsPlayed + "," + (j + playerCareer.get(0)._year - yearBorn - 1) + "," + playerCareer.get(j)._averageActualStrokes + "," + "\n");
						}
						totalScore += playerCareer.get(j)._averageActualStrokes;
					}
					eventsPlayedBuffer.append(nationwide + "," + (totalScore / playerCareer.size()) + "," + (eventsPlayed / times) + "\n");
					
					Double bestScore = 100.0;
					int bestScoreIndex = 0;
					for(int j = 0; j < playerCareer.size(); j++){
						if("PGA".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageActualStrokes > 55 && playerCareer.get(j)._averageActualStrokes < bestScore){
							bestScore = playerCareer.get(j)._averageActualStrokes;
							bestScoreIndex = j;
						}
						else if("Nationwide".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageActualStrokes > 55 && (playerCareer.get(j)._averageActualStrokes + 0.85) < bestScore){
							bestScore = playerCareer.get(j)._averageActualStrokes + 0.85;
							bestScoreIndex = j;
						}
					}
					bestScoreBuffer.append(playerCareer.get(bestScoreIndex)._playerName + "," + (playerCareer.get(bestScoreIndex)._year - yearBorn) + "," + bestScore + "\n");

					for(int j = 0; j < playerCareer.size(); j++){
						if(playerCareer.get(j)._averageActualStrokes > 55){
							if("PGA".equals(playerCareer.get(j)._tour)){
								playerCareerArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = Double.toString(playerCareer.get(j)._averageActualStrokes);
								if(playerCareer.get(j)._averageActualStrokesRank.startsWith("T"))
									playerCareerRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._averageActualStrokesRank.substring(1);
								else
									playerCareerRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._averageActualStrokesRank;
								if(playerCareer.get(j)._earningsRank.startsWith("T"))
									playerCareerMoneyRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._earningsRank.substring(1);
								else
									playerCareerMoneyRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = playerCareer.get(j)._earningsRank;

							}
//							resultBuffer.append(playerCareer.get(j)._averageActualStrokes + ",");
							if("Nationwide".equals(playerCareer.get(j)._tour)){
								playerCareerArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = Double.toString(playerCareer.get(j)._averageActualStrokes + 0.85);
								playerCareerRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = " ";
								playerCareerMoneyRankArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = " ";
							}
//							resultBuffer.append((playerCareer.get(j)._averageActualStrokes + 0.85) + ",");
//							else{
//							resultBuffer.append(",");
//							}
						}
//						else{
//						resultBuffer.append(",");
//						}
					}
					resultBuffer.append("Year,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j]))
							resultBuffer.append((j + playerCareer.get(0)._year) + ",");
					}
					resultBuffer.append("\n");
					
					resultBuffer.append("Age,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j])){
							resultBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
							shortResultBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
						}
						if(!"".equals(playerCareerRankArray[j]) && !" ".equals(playerCareerRankArray[j])){
							ranksBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					ranksBuffer.append("\n");

					resultBuffer.append("Score,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j])){
							resultBuffer.append(playerCareerArray[j] + ",");
							shortResultBuffer.append(playerCareerArray[j] + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					resultBuffer.append("Score Rank,");
					for(int j = 0; j < playerCareerRankArray.length; j++){
						if(!"".equals(playerCareerRankArray[j])){
							resultBuffer.append(playerCareerRankArray[j] + ",");
							shortResultBuffer.append(playerCareerRankArray[j] + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					
					resultBuffer.append("Money Rank,");
					for(int j = 0; j < playerCareerMoneyRankArray.length; j++){
						if(!"".equals(playerCareerMoneyRankArray[j])){
							resultBuffer.append(playerCareerMoneyRankArray[j] + ",");
							shortResultBuffer.append(playerCareerMoneyRankArray[j] + ",");
						}
						if(!"".equals(playerCareerMoneyRankArray[j]) && !" ".equals(playerCareerMoneyRankArray[j])){
							ranksBuffer.append(playerCareerMoneyRankArray[j] + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");
					ranksBuffer.append("\n");

					namesBuffer.append(playerCareer.get(0)._playerName + "\n");
				}
			}

			FileWriter outFile = new FileWriter("Player Careers - PGA Analysis.csv");
			PrintWriter out = new PrintWriter(outFile);
			out.print(resultBuffer);
			out.close();

			FileWriter outFile2 = new FileWriter("Player Careers - Quick Analysis.csv");
			PrintWriter out2 = new PrintWriter(outFile2);
			out2.print(shortResultBuffer);
			out2.close();
			
			FileWriter outFile3 = new FileWriter("Player Careers - Best Scores.csv");
			PrintWriter out3 = new PrintWriter(outFile3);
			out3.print(bestScoreBuffer);
			out3.close();
			
			FileWriter outFile4 = new FileWriter("Player Careers - Events Played.csv");
			PrintWriter out4 = new PrintWriter(outFile4);
			out4.print(eventsPlayedBuffer);
			out4.close();
			
			FileWriter outFile5 = new FileWriter("Player Careers - Events Played By Age PGA.csv");
			PrintWriter out5 = new PrintWriter(outFile5);
			out5.print(eventsPlayedByAgePGABuffer);
			out5.close();
			
			FileWriter outFile6 = new FileWriter("Player Careers - Events Played By Age Nationwide.csv");
			PrintWriter out6 = new PrintWriter(outFile6);
			out6.print(eventsPlayedByAgeNationwideBuffer);
			out6.close();
			
			FileWriter outFile7 = new FileWriter("Player Careers - Money Ranks.csv");
			PrintWriter out7 = new PrintWriter(outFile7);
			out7.print(ranksBuffer);
			out7.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void outputPGAPlayerCareers10To15(){
		StringBuffer resultBuffer = new StringBuffer();
		StringBuffer shortResultBuffer = new StringBuffer();
		StringBuffer namesBuffer = new StringBuffer();

		try{
			File nameFile = new File("Player Careers - Names - 10to15 - Static.csv");
			BufferedReader nameReader = new BufferedReader(new FileReader(nameFile));
			String nameText = null;
			String[][] nameString = new String[185][2];
			int count = 0;
			while((nameText = nameReader.readLine()) != null){
				String[] cut = nameText.split(",");
				nameString[count][0] = cut[0];
				nameString[count][1] = cut[1];
				count++;
			}

			for(int i = 0; i < _sortedListOfCareers.size(); i++){
				ArrayList<Record> playerCareer = _sortedListOfCareers.get(i);			

				if(playerCareer.size() >= 10 && playerCareer.size() <= 15){
					int yearBorn = 0;
					for(int j = 0; j < nameString.length; j++){
						if(nameString[j][0].equals(playerCareer.get(0)._playerName)){
							yearBorn = Integer.parseInt(nameString[j][1]);
						}
					}
					resultBuffer.append("Player Name," + playerCareer.get(0)._playerName + "\n");

//					for(int j = 1; j <= (playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1); j++){
//						resultBuffer.append((j) + ",");
//					}
//					resultBuffer.append("\n");

					String[] playerCareerArray = new String[playerCareer.get(playerCareer.size()-1)._year - playerCareer.get(0)._year + 1];
					for(int j = 0; j < playerCareerArray.length; j++){
						playerCareerArray[j] = "";
					}
					
					Double bestScore = 0.0;
//					for(int j = 0; j < playerCareer.size(); j++){
//						if("PGA".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageActualStrokes > 55 && playerCareer.get(j)._averageActualStrokes < bestScore){
//							bestScore = playerCareer.get(j)._averageActualStrokes;
//						}
//						else if("Nationwide".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageActualStrokes > 55 && (playerCareer.get(j)._averageActualStrokes + 0.85) < bestScore){
//							bestScore = playerCareer.get(j)._averageActualStrokes + 0.85;
//						}
//					}
					for(int j = 0; j < playerCareer.size(); j++){
						if(playerCareer.get(j)._averageActualStrokes > 55){
							if("PGA".equals(playerCareer.get(j)._tour))
								playerCareerArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = Double.toString(playerCareer.get(j)._averageActualStrokes - bestScore);
//							resultBuffer.append(playerCareer.get(j)._averageActualStrokes + ",");
							if("Nationwide".equals(playerCareer.get(j)._tour))
								playerCareerArray[playerCareer.get(j)._year - playerCareer.get(0)._year] = Double.toString(playerCareer.get(j)._averageActualStrokes + 0.85 - bestScore);
//							resultBuffer.append((playerCareer.get(j)._averageActualStrokes + 0.85) + ",");
//							else{
//							resultBuffer.append(",");
//							}
						}
//						else{
//						resultBuffer.append(",");
//						}
					}
					resultBuffer.append("Year,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j]))
							resultBuffer.append((j + playerCareer.get(0)._year) + ",");
					}
					resultBuffer.append("\n");
					
					resultBuffer.append("Age,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j])){
							resultBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
							shortResultBuffer.append((j + playerCareer.get(0)._year - yearBorn - 1) + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");

					resultBuffer.append("Relative score,");
					for(int j = 0; j < playerCareerArray.length; j++){
						if(!"".equals(playerCareerArray[j])){
							resultBuffer.append(playerCareerArray[j] + ",");
							shortResultBuffer.append(playerCareerArray[j] + ",");
						}
					}
					resultBuffer.append("\n");
					shortResultBuffer.append("\n");

					namesBuffer.append(playerCareer.get(0)._playerName + "\n");
				}
			}

			FileWriter outFile = new FileWriter("Player Careers - PGA Analysis - 10to15.csv");
			PrintWriter out = new PrintWriter(outFile);
			out.print(resultBuffer);
			out.close();

			FileWriter outFile2 = new FileWriter("Player Careers - Quick Analysis - 10to15.csv");
			PrintWriter out2 = new PrintWriter(outFile2);
			out2.print(shortResultBuffer);
			out2.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void outputNationwideEntryAge(){
		StringBuffer nationwideEntryAge = new StringBuffer();
		StringBuffer nationwideEntries = new StringBuffer();
		StringBuffer moneyISLCalibration = new StringBuffer();
		
		for(int i = 0; i < _sortedListOfCareers.size(); i++){
			if("Nationwide".equals(_sortedListOfCareers.get(i).get(0)._tour)){
				nationwideEntries.append(_sortedListOfCareers.get(i).get(0)._playerName + "," + 
						_sortedListOfCareers.get(i).get(0)._year + "\n");
			}
			if("Nationwide".equals(_sortedListOfCareers.get(i).get(0)._tour) && _sortedListOfCareers.get(i).get(0)._age > 0){
				double averageAdjustedScore = 0;
				if(_sortedListOfCareers.get(i).get(0)._year > 1997){
					averageAdjustedScore = _sortedListOfCareers.get(i).get(0)._averageAdjustedStrokes;
				}
				nationwideEntryAge.append(_sortedListOfCareers.get(i).get(0)._playerName + "," + 
						_sortedListOfCareers.get(i).get(0)._year + "," + _sortedListOfCareers.get(i).get(0)._age 
						+ "," + _sortedListOfCareers.get(i).get(0)._averageActualStrokes + "," + averageAdjustedScore 
						+ "," + _sortedListOfCareers.get(i).get(0)._eventsPlayed + "," + _sortedListOfCareers.get(i).size() + "\n");
			}
			String earningsRank = "";
			String averageActualRank = "";
			String averageAdjustedRank = "";
			for(int j = 0; j < _sortedListOfCareers.get(i).size(); j++){
				if("PGA".equals(_sortedListOfCareers.get(i).get(j)._tour)
						&& _sortedListOfCareers.get(i).get(j)._year > 1997
						&& _sortedListOfCareers.get(i).get(j)._averageActualStrokes > 55
						&& _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokes > 55){
					if(_sortedListOfCareers.get(i).get(j)._earningsRank.startsWith("T"))
						earningsRank = _sortedListOfCareers.get(i).get(j)._earningsRank.substring(1);
					else
						earningsRank = _sortedListOfCareers.get(i).get(j)._earningsRank;
					if(_sortedListOfCareers.get(i).get(j)._averageActualStrokesRank.startsWith("T"))
						averageActualRank = _sortedListOfCareers.get(i).get(j)._averageActualStrokesRank.substring(1);
					else
						averageActualRank = _sortedListOfCareers.get(i).get(j)._averageActualStrokesRank;
					if(_sortedListOfCareers.get(i).get(j)._averageAdjustedStrokesRank.startsWith("T"))
						averageAdjustedRank = _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokesRank.substring(1);
					else
						averageAdjustedRank = _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokesRank;

					
					moneyISLCalibration.append("0" + "," + earningsRank
							+ "," + averageActualRank + ","
							+ _sortedListOfCareers.get(i).get(j)._averageActualStrokes + ","
							+ averageAdjustedRank + ","
							+ _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokes + "\n");
				}
				else if("Nationwide".equals(_sortedListOfCareers.get(i).get(j)._tour)
						&& _sortedListOfCareers.get(i).get(j)._year > 1997
						&& _sortedListOfCareers.get(i).get(j)._averageActualStrokes > 55
						&& _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokes > 55){
					
					if(_sortedListOfCareers.get(i).get(j)._earningsRank.startsWith("T"))
						earningsRank = _sortedListOfCareers.get(i).get(j)._earningsRank.substring(1);
					else
						earningsRank = _sortedListOfCareers.get(i).get(j)._earningsRank;
					if(_sortedListOfCareers.get(i).get(j)._averageActualStrokesRank.startsWith("T"))
						averageActualRank = _sortedListOfCareers.get(i).get(j)._averageActualStrokesRank.substring(1);
					else
						averageActualRank = _sortedListOfCareers.get(i).get(j)._averageActualStrokesRank;
					if(_sortedListOfCareers.get(i).get(j)._averageAdjustedStrokesRank.startsWith("T"))
						averageAdjustedRank = _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokesRank.substring(1);
					else
						averageAdjustedRank = _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokesRank;
					moneyISLCalibration.append("1" + "," + earningsRank
							+ "," + averageActualRank + ","
							+ _sortedListOfCareers.get(i).get(j)._averageActualStrokes + ","
							+ averageAdjustedRank + ","
							+ _sortedListOfCareers.get(i).get(j)._averageAdjustedStrokes + "\n");
				}
			}
		}
		try{
			FileWriter outFile = new FileWriter("NationwideEntryAge.csv");
			PrintWriter out = new PrintWriter(outFile);
			out.print(nationwideEntryAge);
			out.close();
			
			FileWriter outFile2 = new FileWriter("NationwideEntries.csv");
			PrintWriter out2 = new PrintWriter(outFile2);
			out2.print(nationwideEntries);
			out2.close();
			
			FileWriter outFile3 = new FileWriter("MoneyISLCalibration.csv");
			PrintWriter out3 = new PrintWriter(outFile3);
			out3.print(moneyISLCalibration);
			out3.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void outputPGAEntryAge(){
		StringBuffer pgaEntryAge = new StringBuffer();
		
		for(int i = 0; i < _sortedListOfCareers.size(); i++){
			if("PGA".equals(_sortedListOfCareers.get(i).get(0)._tour) && _sortedListOfCareers.get(i).get(0)._age > 0 && _sortedListOfCareers.get(i).get(0)._year > 1987){
				double averageAdjustedScore = _sortedListOfCareers.get(i).get(0)._averageAdjustedStrokes;
				pgaEntryAge.append(_sortedListOfCareers.get(i).get(0)._playerName + "," + 
						_sortedListOfCareers.get(i).get(0)._year + "," + _sortedListOfCareers.get(i).get(0)._age 
						+ "," + _sortedListOfCareers.get(i).get(0)._averageActualStrokes + "," + averageAdjustedScore 
						+ "," + _sortedListOfCareers.get(i).get(0)._eventsPlayed + "," + _sortedListOfCareers.get(i).size() + "\n");
			}
		}
		try{
			FileWriter outFile = new FileWriter("PGAEntryAge.csv");
			PrintWriter out = new PrintWriter(outFile);
			out.print(pgaEntryAge);
			out.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void outputYearlyScoringData(){
		ArrayList<StringBuffer> yearlyPGABuffer = new ArrayList<StringBuffer>();
		ArrayList<StringBuffer> yearlyNationwideBuffer = new ArrayList<StringBuffer>();
		// One for each year from 1998-2010 (13 years total)
		for(int i = 0; i < 13; i++){
			yearlyPGABuffer.add(new StringBuffer());
			yearlyNationwideBuffer.add(new StringBuffer());
		}
		for(int i = 0; i < _sortedListOfCareers.size(); i++){
			ArrayList<Record> playerCareer = _sortedListOfCareers.get(i);
			for(int j = 0; j < playerCareer.size(); j++){
				if(playerCareer.get(j)._year > 1997 && "PGA".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageAdjustedStrokes > 55 && playerCareer.get(j)._averageAdjustedStrokes < 85){
					yearlyPGABuffer.get(playerCareer.get(j)._year-1998).append(playerCareer.get(j)._averageAdjustedStrokes + "\n");
				}
				else if(playerCareer.get(j)._year > 1997 && "Nationwide".equals(playerCareer.get(j)._tour) && playerCareer.get(j)._averageAdjustedStrokes > 55 && playerCareer.get(j)._averageAdjustedStrokes < 85){
					yearlyNationwideBuffer.get(playerCareer.get(j)._year-1998).append(playerCareer.get(j)._averageAdjustedStrokes + "\n");	
				}
			}
		}
		
		try{
			FileWriter outFilePGAAdjustedComplete = new FileWriter("PGA yearly adjusted.csv");
			PrintWriter outPGAAdjustedComplete = new PrintWriter(outFilePGAAdjustedComplete);
			for(int i = 0; i < yearlyPGABuffer.size(); i++){
				outPGAAdjustedComplete.print(yearlyPGABuffer.get(i));
			}
			outPGAAdjustedComplete.close();
			
			FileWriter outFileNationwideAdjustedComplete = new FileWriter("Nationwide yearly adjusted.csv");
			PrintWriter outNationwideAdjustedComplete = new PrintWriter(outFileNationwideAdjustedComplete);
			for(int i = 0; i < yearlyNationwideBuffer.size(); i++){
				outNationwideAdjustedComplete.print(yearlyNationwideBuffer.get(i));
			}
			outNationwideAdjustedComplete.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void outputYearlyAgeData(){
		StringBuffer ageBuffer;
		StringBuffer agePGACombined = new StringBuffer();
		StringBuffer ageNationwideCombined = new StringBuffer();
		
		for(int year = 2004; year < 2011; year++){
			ageBuffer = new StringBuffer();
			for(int i = 0; i < _sortedListOfCareers.size(); i++){
				for(int j = 0; j < _sortedListOfCareers.get(i).size(); j++){
					if(_sortedListOfCareers.get(i).get(j)._year == year && _sortedListOfCareers.get(i).get(j)._age > 0 && "PGA".equals(_sortedListOfCareers.get(i).get(j)._tour)){
						ageBuffer.append(_sortedListOfCareers.get(i).get(j)._playerName + "," + _sortedListOfCareers.get(i).get(j)._age + "\n");
						agePGACombined.append(_sortedListOfCareers.get(i).get(j)._playerName + "," + _sortedListOfCareers.get(i).get(j)._age + "\n");
					}
				}
			}
			
			try{
				FileWriter outFile = new FileWriter("Yearly age PGA - " + year + ".csv");
				PrintWriter out = new PrintWriter(outFile);
				out.print(ageBuffer);
				out.close();
				
				FileWriter outFile2 = new FileWriter("Yearly age PGA combined.csv");
				PrintWriter out2 = new PrintWriter(outFile2);
				out2.print(agePGACombined);
				out2.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int year = 2004; year < 2011; year++){
			ageBuffer = new StringBuffer();
			for(int i = 0; i < _sortedListOfCareers.size(); i++){
				for(int j = 0; j < _sortedListOfCareers.get(i).size(); j++){
					if(_sortedListOfCareers.get(i).get(j)._year == year && _sortedListOfCareers.get(i).get(j)._age > 0 && "Nationwide".equals(_sortedListOfCareers.get(i).get(j)._tour)){
						ageBuffer.append(_sortedListOfCareers.get(i).get(j)._playerName + "," + _sortedListOfCareers.get(i).get(j)._age + "\n");
						ageNationwideCombined.append(_sortedListOfCareers.get(i).get(j)._playerName + "," + _sortedListOfCareers.get(i).get(j)._age + "\n");
					}
				}
			}
			
			try{
				FileWriter outFile = new FileWriter("Yearly age Nationwide - " + year + ".csv");
				PrintWriter out = new PrintWriter(outFile);
				out.print(ageBuffer);
				out.close();
				
				FileWriter outFile2 = new FileWriter("Yearly age Nationwide combined.csv");
				PrintWriter out2 = new PrintWriter(outFile2);
				out2.print(ageNationwideCombined);
				out2.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void outputCompleteHistogramData(){
		StringBuffer histogramPGAActualCompleteBuffer = new StringBuffer();
		StringBuffer histogramNationwideActualCompleteBuffer = new StringBuffer();
		StringBuffer histogramPGAAdjustedCompleteBuffer = new StringBuffer();
		StringBuffer histogramNationwideAdjustedCompleteBuffer = new StringBuffer();
		StringBuffer histogramPGABeforeCutCompleteBuffer = new StringBuffer();
		StringBuffer histogramNationwideBeforeCutCompleteBuffer = new StringBuffer();
		StringBuffer histogramPGAAfterCutCompleteBuffer = new StringBuffer();
		StringBuffer histogramNationwideAfterCutCompleteBuffer = new StringBuffer();
		
		int startIndex = 0;
		int endIndex = 0;
		for(int i = 0; i < _histogramPGAActualComplete.length; i++){
			if(_histogramPGAActualComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramPGAActualComplete.length-1; i >= 0; i--){
			if(_histogramPGAActualComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramPGAActualCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramPGAActualComplete[i] + "\n");
		}
	    
		for(int i = 0; i < _histogramNationwideActualComplete.length; i++){
			if(_histogramNationwideActualComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramNationwideActualComplete.length-1; i >= 0; i--){
			if(_histogramNationwideActualComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramNationwideActualCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramNationwideActualComplete[i] + "\n");
		}
		
		for(int i = 0; i < _histogramPGAAdjustedComplete.length; i++){
			if(_histogramPGAAdjustedComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramPGAAdjustedComplete.length-1; i >= 0; i--){
			if(_histogramPGAAdjustedComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramPGAAdjustedCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramPGAAdjustedComplete[i] + "\n");
		}
		
		for(int i = 0; i < _histogramNationwideAdjustedComplete.length; i++){
			if(_histogramNationwideAdjustedComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramNationwideAdjustedComplete.length-1; i >= 0; i--){
			if(_histogramNationwideAdjustedComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramNationwideAdjustedCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramNationwideAdjustedComplete[i] + "\n");
		}
		
		for(int i = 0; i < _histogramPGABeforeCutComplete.length; i++){
			if(_histogramPGABeforeCutComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramPGABeforeCutComplete.length-1; i >= 0; i--){
			if(_histogramPGABeforeCutComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramPGABeforeCutCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramPGABeforeCutComplete[i] + "\n");
		}
		
		for(int i = 0; i < _histogramNationwideBeforeCutComplete.length; i++){
			if(_histogramNationwideBeforeCutComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramNationwideBeforeCutComplete.length-1; i >= 0; i--){
			if(_histogramNationwideBeforeCutComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramNationwideBeforeCutCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramNationwideBeforeCutComplete[i] + "\n");
		}
		
		for(int i = 0; i < _histogramPGAAfterCutComplete.length; i++){
			if(_histogramPGAAfterCutComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramPGAAfterCutComplete.length-1; i >= 0; i--){
			if(_histogramPGAAfterCutComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramPGAAfterCutCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramPGAAfterCutComplete[i] + "\n");
		}
		
		for(int i = 0; i < _histogramNationwideAfterCutComplete.length; i++){
			if(_histogramNationwideAfterCutComplete[i] > 0){
				startIndex = i;
				break;
			}
		}
		for(int i = _histogramNationwideAfterCutComplete.length-1; i >= 0; i--){
			if(_histogramNationwideAfterCutComplete[i] > 0){
				endIndex = i;
				break;
			}
		}
		for(int i = startIndex; i <= endIndex; i++){
			histogramNationwideAfterCutCompleteBuffer.append(roundOneDecimal(((double)i)/10) + "," + _histogramNationwideAfterCutComplete[i] + "\n");
		}
				
		try{			
			FileWriter outFilePGAActualComplete = new FileWriter("histograms/Histogram PGA actual complete.csv");
			PrintWriter outPGAActualComplete = new PrintWriter(outFilePGAActualComplete);
			outPGAActualComplete.print(histogramPGAActualCompleteBuffer);
			outPGAActualComplete.close();
			
			FileWriter outFileNationwideActualComplete = new FileWriter("histograms/Histogram Nationwide actual complete.csv");
			PrintWriter outNationwideActualComplete = new PrintWriter(outFileNationwideActualComplete);
			outNationwideActualComplete.print(histogramNationwideActualCompleteBuffer);
			outNationwideActualComplete.close();
			
			FileWriter outFilePGAAdjustedComplete = new FileWriter("histograms/Histogram PGA adjusted complete.csv");
			PrintWriter outPGAAdjustedComplete = new PrintWriter(outFilePGAAdjustedComplete);
			outPGAAdjustedComplete.print(histogramPGAAdjustedCompleteBuffer);
			outPGAAdjustedComplete.close();
			
			FileWriter outFileNationwideAdjustedComplete = new FileWriter("histograms/Histogram Nationwide adjusted complete.csv");
			PrintWriter outNationwideAdjustedComplete = new PrintWriter(outFileNationwideAdjustedComplete);
			outNationwideAdjustedComplete.print(histogramNationwideAdjustedCompleteBuffer);
			outNationwideAdjustedComplete.close();
			
			FileWriter outFilePGABeforeCutComplete = new FileWriter("histograms/Histogram PGA before cut complete.csv");
			PrintWriter outPGABeforeCutComplete = new PrintWriter(outFilePGABeforeCutComplete);
			outPGABeforeCutComplete.print(histogramPGABeforeCutCompleteBuffer);
			outPGABeforeCutComplete.close();
			
			FileWriter outFileNationwideBeforeCutComplete = new FileWriter("histograms/Histogram Nationwide before cut complete.csv");
			PrintWriter outNationwideBeforeCutComplete = new PrintWriter(outFileNationwideBeforeCutComplete);
			outNationwideBeforeCutComplete.print(histogramNationwideBeforeCutCompleteBuffer);
			outNationwideBeforeCutComplete.close();
			
			FileWriter outFilePGAAfterCutComplete = new FileWriter("histograms/Histogram PGA after cut complete.csv");
			PrintWriter outPGAAfterCutComplete = new PrintWriter(outFilePGAAfterCutComplete);
			outPGAAfterCutComplete.print(histogramPGAAfterCutCompleteBuffer);
			outPGAAfterCutComplete.close();
			
			FileWriter outFileNationwideAfterCutComplete = new FileWriter("histograms/Histogram Nationwide after cut complete.csv");
			PrintWriter outNationwideAfterCutComplete = new PrintWriter(outFileNationwideAfterCutComplete);
			outNationwideAfterCutComplete.print(histogramNationwideAfterCutCompleteBuffer);
			outNationwideAfterCutComplete.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void outputYearlyHistogramData(){
		for(int i = 0; i < _histogramPGAActualYearly.size(); i++){
			StringBuffer histogramPGAActualYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramPGAActualYearly.get(i).length; j++){
				if(_histogramPGAActualYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramPGAActualYearly.get(i).length-1; j >= 0; j--){
				if(_histogramPGAActualYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramPGAActualYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramPGAActualYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFilePGAActualYearly = new FileWriter("histograms/yearly/Histogram PGA actual " + (2010-i) + ".csv");
				PrintWriter outPGAActualYearly = new PrintWriter(outFilePGAActualYearly);
				outPGAActualYearly.print(histogramPGAActualYearlyBuffer);
				outPGAActualYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < _histogramNationwideActualYearly.size(); i++){
			StringBuffer histogramNationwideActualYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramNationwideActualYearly.get(i).length; j++){
				if(_histogramNationwideActualYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramNationwideActualYearly.get(i).length-1; j >= 0; j--){
				if(_histogramNationwideActualYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramNationwideActualYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramNationwideActualYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFileNationwideActualYearly = new FileWriter("histograms/yearly/Histogram Nationwide actual " + (2010-i) + ".csv");
				PrintWriter outNationwideActualYearly = new PrintWriter(outFileNationwideActualYearly);
				outNationwideActualYearly.print(histogramNationwideActualYearlyBuffer);
				outNationwideActualYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < _histogramPGAAdjustedYearly.size(); i++){
			StringBuffer histogramPGAAdjustedYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramPGAAdjustedYearly.get(i).length; j++){
				if(_histogramPGAAdjustedYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramPGAAdjustedYearly.get(i).length-1; j >= 0; j--){
				if(_histogramPGAAdjustedYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramPGAAdjustedYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramPGAAdjustedYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFilePGAAdjustedYearly = new FileWriter("histograms/yearly/Histogram PGA adjusted " + (2010-i) + ".csv");
				PrintWriter outPGAAdjustedYearly = new PrintWriter(outFilePGAAdjustedYearly);
				outPGAAdjustedYearly.print(histogramPGAAdjustedYearlyBuffer);
				outPGAAdjustedYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < _histogramNationwideAdjustedYearly.size(); i++){
			StringBuffer histogramNationwideAdjustedYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramNationwideAdjustedYearly.get(i).length; j++){
				if(_histogramNationwideAdjustedYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramNationwideAdjustedYearly.get(i).length-1; j >= 0; j--){
				if(_histogramNationwideAdjustedYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramNationwideAdjustedYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramNationwideAdjustedYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFileNationwideAdjustedYearly = new FileWriter("histograms/yearly/Histogram Nationwide adjusted " + (2010-i) + ".csv");
				PrintWriter outNationwideAdjustedYearly = new PrintWriter(outFileNationwideAdjustedYearly);
				outNationwideAdjustedYearly.print(histogramNationwideAdjustedYearlyBuffer);
				outNationwideAdjustedYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < _histogramPGABeforeCutYearly.size(); i++){
			StringBuffer histogramPGABeforeCutYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramPGABeforeCutYearly.get(i).length; j++){
				if(_histogramPGABeforeCutYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramPGABeforeCutYearly.get(i).length-1; j >= 0; j--){
				if(_histogramPGABeforeCutYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramPGABeforeCutYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramPGABeforeCutYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFilePGABeforeCutYearly = new FileWriter("histograms/yearly/Histogram PGA before cut " + (2010-i) + ".csv");
				PrintWriter outPGABeforeCutYearly = new PrintWriter(outFilePGABeforeCutYearly);
				outPGABeforeCutYearly.print(histogramPGABeforeCutYearlyBuffer);
				outPGABeforeCutYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < _histogramNationwideBeforeCutYearly.size(); i++){
			StringBuffer histogramNationwideBeforeCutYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramNationwideBeforeCutYearly.get(i).length; j++){
				if(_histogramNationwideBeforeCutYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramNationwideBeforeCutYearly.get(i).length-1; j >= 0; j--){
				if(_histogramNationwideBeforeCutYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramNationwideBeforeCutYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramNationwideBeforeCutYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFileNationwideBeforeCutYearly = new FileWriter("histograms/yearly/Histogram Nationwide before cut " + (2010-i) + ".csv");
				PrintWriter outNationwideBeforeCutYearly = new PrintWriter(outFileNationwideBeforeCutYearly);
				outNationwideBeforeCutYearly.print(histogramNationwideBeforeCutYearlyBuffer);
				outNationwideBeforeCutYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < _histogramPGAAfterCutYearly.size(); i++){
			StringBuffer histogramPGAAfterCutYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramPGAAfterCutYearly.get(i).length; j++){
				if(_histogramPGAAfterCutYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramPGAAfterCutYearly.get(i).length-1; j >= 0; j--){
				if(_histogramPGAAfterCutYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramPGAAfterCutYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramPGAAfterCutYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFilePGAAfterCutYearly = new FileWriter("histograms/yearly/Histogram PGA after cut " + (2010-i) + ".csv");
				PrintWriter outPGAAfterCutYearly = new PrintWriter(outFilePGAAfterCutYearly);
				outPGAAfterCutYearly.print(histogramPGAAfterCutYearlyBuffer);
				outPGAAfterCutYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < _histogramNationwideAfterCutYearly.size(); i++){
			StringBuffer histogramNationwideAfterCutYearlyBuffer = new StringBuffer();
			int startIndex = 0;
			int endIndex = 0;
			for(int j = 0; j < _histogramNationwideAfterCutYearly.get(i).length; j++){
				if(_histogramNationwideAfterCutYearly.get(i)[j] > 0){
					startIndex = j;
					break;
				}
			}
			for(int j = _histogramNationwideAfterCutYearly.get(i).length-1; j >= 0; j--){
				if(_histogramNationwideAfterCutYearly.get(i)[j] > 0){
					endIndex = j;
					break;
				}
			}
			for(int j = startIndex; j <= endIndex; j++){
				histogramNationwideAfterCutYearlyBuffer.append(roundOneDecimal(((double)j)/10) + "," + _histogramNationwideAfterCutYearly.get(i)[j] + "\n");
			}

			try{			
				FileWriter outFileNationwideAfterCutYearly = new FileWriter("histograms/yearly/Histogram Nationwide after cut " + (2010-i) + ".csv");
				PrintWriter outNationwideAfterCutYearly = new PrintWriter(outFileNationwideAfterCutYearly);
				outNationwideAfterCutYearly.print(histogramNationwideAfterCutYearlyBuffer);
				outNationwideAfterCutYearly.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main (String[] args){
		convertToRecordsActual();
		convertToRecordsAdjusted();
		convertToRecordsMoneyList();
		convertToRecordsBeforeCut();
		convertToRecordsAge();
//		joinRecords();
		sortRecordsByNames();
		sortPlayerCareerByTime();
		roundTwoDecimals();
		outputPlayerCareers();
//		outputPGAPlayerCareers10To15();
		outputPGAPlayerCareers();
		outputNationwideEntryAge();
		outputPGAEntryAge();
		outputYearlyScoringData();
		outputYearlyAgeData();
		outputCompleteHistogramData();
		outputYearlyHistogramData();
		System.out.println("Done!");
	}
}
