import java.text.DecimalFormat;


public class Record {

	public String _playerName;
	public int _year;
	public int _age;
	public String _tour;
	public double _averageActualStrokes;
	public String _averageActualStrokesRank;
	public double _averageAdjustedStrokes;
	public String _averageAdjustedStrokesRank;
	public double _averageBeforeCutStrokes;
	public String _averageBeforeCutRank;
	public int _totalStrokesBeforeCut;
	public int _totalBeforeCutRounds;
	public double _averageAfterCutStrokes;
	//public String _averageAfterCutRank;
	public int _totalStrokesAfterCut;
	public int _totalAfterCutRounds;
	public int _eventsPlayed;
	public int _earnings;
	public String _earningsRank;
	public int _totalStrokes;
	public int _rounds;
	
	public Record(String playerName, int year, String tour, double averageActualStrokes, String averageActualStrokesRank, int totalStrokes, int rounds){
		_playerName = playerName;
		_year = year;
		_tour = tour;
		_averageActualStrokes = averageActualStrokes;
		_averageActualStrokesRank = averageActualStrokesRank;
		_totalStrokes = totalStrokes;
		_rounds = rounds;
		
		_averageAdjustedStrokes = 0;
		_averageAdjustedStrokesRank = "";
		_earnings = 0;
		_earningsRank = "";
		_averageBeforeCutStrokes = 0;
		_averageBeforeCutRank = "";
		_totalStrokesBeforeCut = 0;
		_totalBeforeCutRounds = 0;
		_averageAfterCutStrokes = 0;
		_totalStrokesAfterCut = 0;
		_totalAfterCutRounds = 0;
	}
	
	public Record(String playerName, int year, String tour, double averageAdjustedStrokes, String averageAdjustedStrokesRank, int totalStrokes, int rounds, boolean adjusted){
		_playerName = playerName;
		_year = year;
		_tour = tour;
		_averageAdjustedStrokes = averageAdjustedStrokes;
		_averageAdjustedStrokesRank = averageAdjustedStrokesRank;
		_totalStrokes = totalStrokes;
		_rounds = rounds;
	}
	
	public Record(String playerName, int year, String tour, int eventsPlayed, int earnings, String earningsRank){
		_playerName = playerName;
		_year = year;
		_tour = tour;
		_eventsPlayed = eventsPlayed;
		_earnings = earnings;
		_earningsRank = earningsRank;
	}
	
	public Record(String playerName, int year, String tour, double averageBeforeCutStrokes, String averageBeforeCutRank, int totalStrokesBeforeCut, int rounds, boolean adjusted, boolean beforeCut){
		_playerName = playerName;
		_year = year;
		_tour = tour;
		_averageBeforeCutStrokes = averageBeforeCutStrokes;
		_averageBeforeCutRank = averageBeforeCutRank;
		_totalStrokesBeforeCut = totalStrokesBeforeCut;
		_totalBeforeCutRounds = rounds;
	}
	
	public void roundTwoDecimals(){
		_averageActualStrokes = roundTwoDecimals(_averageActualStrokes);
		_averageAdjustedStrokes = roundTwoDecimals(_averageAdjustedStrokes);
		_averageBeforeCutStrokes = roundTwoDecimals(_averageBeforeCutStrokes);
		_averageAfterCutStrokes = roundTwoDecimals(_averageAfterCutStrokes);
	}
	
	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
