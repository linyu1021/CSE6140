import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.RuntimeException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DP {

	public static Data maxSubArray(double[] rates) throws RuntimeException {
        if (rates == null || rates.length == 0) {
        	System.err.println("The input is empty.");
        	System.exit(1);
        }
        
        Data[] dp = new Data[rates.length + 1];
        dp[0] = new Data(0, 0, 0);
        
        for (int i = 1; i < dp.length; i++) {
            //double temp = dp[i - 1].sum + rates[i - 1];
            dp[i] = new Data(0, 0, 0);
            if (dp[i - 1].sum + rates[i - 1] > 0) {
                dp[i].sum = dp[i - 1].sum + rates[i - 1];
                dp[i].start = dp[i - 1].start;
                dp[i].end = i - 1;
                
            } else {
                dp[i].sum = 0;
                dp[i].start = i;
                dp[i].end = i;  
            }
        }

        Data maxSum = new Data(0, 0, 0);

        for (int i = 0; i < dp.length; i++) {
            if (dp[i].sum >= maxSum.sum) {
                maxSum = dp[i];
            }
        }

        return maxSum;
    }


	private static void writeData(double[][] results, String outputFile) throws IOException {
		PrintWriter output = new PrintWriter(outputFile, "UTF-8");
		for (int i = 0; i < results.length; i++) {
			// for (int j = 0; j < results[i].length; j++) {
			// 	output.print(results[i][j] + ",");
			// }
			DecimalFormat df = new DecimalFormat("#.00");  
			output.print(df.format(results[i][0]) + ",");
			output.print((int) results[i][1] + ",");
			output.print((int) results[i][2] + ",");
			output.print(df.format(results[i][3]));
			output.println();
		}
		output.close();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("Usage: <input-graph-file> <heist-nodes-file> <output-file>");
			System.exit(1);
		}

		String rateFilePath = args[0];
		String outputFilePath = args[1];

		BufferedReader br = new BufferedReader(new FileReader(rateFilePath));
		String firstLine = br.readLine();
		int numDays = Integer.parseInt(firstLine.split(",")[0]);
		int numCases = Integer.parseInt(firstLine.split(",")[1]);

		double[][] results = new double[numCases][4];
		
		// Read through the file parsing interest rates
		for (int i = 0; i < numCases; i++) {
			String line = br.readLine();
			double[] rates = new double[numDays];
			
			for (int j = 0; j < numDays; j++) {
				rates[j] = Double.parseDouble(line.split(",")[j]);
				//System.out.print(rates[j]);
			}
			//System.out.println();

			double time_begin = System.nanoTime();
            Data maxSum = maxSubArray(rates);
            double time_end = System.nanoTime();
            double time = (time_end - time_begin) / 1000;
            results[i][0] = maxSum.sum;
            results[i][1] = maxSum.start + 1;
            results[i][2] = maxSum.end + 1;
            results[i][3] = time;
		}
		br.close();
		writeData(results, outputFilePath);
	}
}