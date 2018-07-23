package snowblossom.miner;

import java.util.TreeMap;
import duckutil.RateReporter;
import java.text.DecimalFormat;
import duckutil.AtomicFileOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;

public class ReportManager
{
  private static final Logger logger = Logger.getLogger("snowblossom.miner");

  TreeMap<String, RateReporter> rate_map;
  RateReporter total;
  
  
  public ReportManager()
  {
    total = new RateReporter();
    rate_map = new TreeMap<>();

  }

  public synchronized void record(String address, long count)
  {
    total.record(count);
    if (!rate_map.containsKey(address))
    {
      rate_map.put(address, new  RateReporter());
    }
    rate_map.get(address).record(count);
  }

  public RateReporter getTotalRate()
  {
    return total;
  }

  public synchronized void writeReport(String path, Map<String, String> netstate, Map<String, Double> share, Map<String,Integer> blockfound)
  {
    try
    {

      PrintStream out = new PrintStream(new AtomicFileOutputStream( path ));

      DecimalFormat df = new DecimalFormat("0.0");
      out.println("{\n\"poolhash\":\"" + total.getReportLong(df) + "\",");
      for(Map.Entry<String, String> me : netstate.entrySet())
      {
          out.println(String.format("\"%s\":\"%s\",", me.getKey(), me.getValue()));
      }
      //hashrate
      out.println("\"hashrate\": [");
      TreeSet<String> to_remove = new TreeSet<>();
      for(Map.Entry<String, RateReporter> me : rate_map.entrySet())
      {
        if (me.getValue().isZero())
        {
          to_remove.add(me.getKey());
        }
        else
        {
         out.println(String.format("{\"account\":\"%s\", \"hashrate\":\"%s\"},", me.getKey(), me.getValue().getReportLong(df)));
        }
      }

      for(String k : to_remove)
      {
        rate_map.remove(k);
      }
      out.println("{\"account\":\"hi\",\"hashrate\":\"hi\"}\n],");
      //blockfound.
      out.println("\"blockfound\": [");
      for(Map.Entry<String, Integer> me : blockfound.entrySet())
      {
          out.println(String.format("{\"account\":\"%s\", \"blockNum\":\"%s\"},", me.getKey(), me.getValue()));
      }
      out.println("{\"account\":\"hi\",\"blockNum\":\"hi\"}\n],");  
      //share.
      out.println("\"share\": [");
      for(Map.Entry<String, Double> me : share.entrySet())
      {
          out.println(String.format("{\"account\":\"%s\", \"shares\":\"%s\"},", me.getKey(), me.getValue()));
      }
      out.println("{\"account\":\"Hi\", \"shares\":\"Hi\"}\n]");
      //end without ,
      out.println("}");
      out.flush();
      out.close();
    }
    catch(Exception e)
    {
      logger.log(Level.WARNING, "Error writing report: " + e.toString());
    }

  }

}
