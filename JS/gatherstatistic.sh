#!/bin/bash
for p in 0.0 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.0; do 
  scala -J-Xmx6g autocomplete.GenerateAutocomplete -p $p -s 1000 data *txt >&/dev/null ;
  echo $p $(wc data/*|wc -l ) $(wc -l data/* |sort -n|head -1) $(cat data/*|sort|uniq|wc -l) $(du -s data|cut -f 1) >>result.lst;
  cat result.lst;
  rm data/*;
done

