# modular-programming

Design a simple ETL process – Extract Transform and Load

First Part
	
	• Read files from a directory
	• For each file read contents of the file
	• Capitalize the contents of the file
	• Then write the capitalized content into another output file with the same name in a different directory

Second Part:

	• Read all the unique words from the file
	• For each word calculate the word count
	• For example: for a file containing the content
		
		“Hello world, hello”
		In this case the summary would be
		hello -> 2
		world -> 1

	• For simplicity cases should be ignored – Hello and hello mean the same word
	• This summary should then be added to an output file with the same name in a different directory

Design Considerations:

	• It should be easy to add or remove transformations
	• Input source could change i.e. –
	• In future we could read contents from a SQL table
	• Output source could also change i.e –
	• In future we could write contents to a SQL table
