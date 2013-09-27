del %outDir%\*.* /Q


Call Rebot --name %Report_Title% ^
           --doc %Report_Doc% ^
		   --noncritical %tagInclusion2% ^
	       %tagsCombine% ^
		   --removekeywords passed ^
	       --removekeywords for ^
		   --outputdir %outDir% ^
		   --output output.xml  ^
		   %outputxml%

:END