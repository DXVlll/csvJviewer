# csvJviewer
Simple offline java csv viewer (not editor). It works only with semicolon-separated values.

If you want to test this program, you should download code, then navigate to folder "csv viewer" through the command line and then enter these two commands:

javac -sourcepath src -d bin -classpath lib src/CSV518/simpleViewer/CSVmain.java

java -classpath ./bin CSV518/simpleViewer/CSVmain

Once you run the program, you will be able to select and open .csv files, edit existing fields (but not add new ones!) and save the current table as a .csv file.
