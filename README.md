autocomplete
============

This code supports an idea that in modern time it's better to avoid any server side computation until enormous computation power is needed. One case when a value of server-side computations is exaggerated is an autocomplete: a prompt of the possible words that contain on the base of  entered substring.

Usual realization is quite simple: client requests a list of possible words on a server, sometimes caching a response. It works fine, but requires server-side database. 

But server can contain a precomputed set of the words: a bunch of files, each  contains whole set of the words with the substring. It sounds as an enormous amount of files for each possible substring (~5E04), huh, but really not  all of the substrings are equally useful. 

The trick is how to arrange these words into the set of files. If this set was properly optimized it contains 100000 words distributed in 4000 files, each of them contains less than 1000words (approximately 10K).


| Probability | Amount of wordsets | Minimal size of wordset | Amount of words | Dataset volume |
|-------------|--------------------|-------------------------|-----------------|----------------|
| 0.1         | 105   | 717 | 61158  | 1368  |
| 0.2         | 226   | 539 | 83248  | 2340  |
| 0.3         | 396   | 403 | 96366  | 3540  |
| 0.4         | 634   | 299 | 103481 | 4556  |
| 0.5         | 958   | 220 | 106957 | 5852  |
| 0.6         | 1408  | 153 | 108783 | 7652  |
| 0.7         | 2124  | 94  | 109676 | 10516 |
| 0.8         | 3652  | 40  | 110324 | 16628 |
| 0.9         | 12846 | 1   | 111172 | 53404 |
| 1.0         | 12846 | 1   | 111172 | 53404 |

 
How to use it?
-----------------
A demo html page uses jQuery-UI autocomplete and its code was practically copied from the jQuery-UI demo. The autocomplete.AutocompleteGenerator should compute a set of vocabularies on the base of a large text corpora:

   > scala autocomplete.AutocompleteGenerator [ -p <Double>] [-s <Int>] <Path> [<File> []]

where options are:

   -p <Double> - probability that a substring exists in a dataset (0.8);

   -s <Int> - maximum size of a wordset for a string (1000);

   Path - path to place dataset;

   File - file that contains a text, a few of them can be listed.

sample:

   > scala autocomplete.AutocompleteGenerator data test.txt

A generated files should be placed into a directory /data of web server and you don't need a database any more.





An algorithm.
-----------------
An algorithm is simple, I used a tree (a directed acyclic graph, to be accurate) of sequences where each parent is a substring of each of its child: "ex" is a parent of "ext" that in turn is a  parent of "exte". For substring a set word was found and a size of this set was computed. Each substring went into set if less then 1000 words contains e contained an associated substring, otherwise its child was used until the condition holds.


