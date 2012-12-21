#!C:\perl\Bin
use strict;

#----------------
#Get command line arguments
if ($#ARGV != 2 ) {
	print "usage: Merge_TransFiles.pl <MASTER_FILE> <LANGUAGE_DIR> <OUTPUT_FILE>\n";
	exit;
}

my $masterFile=$ARGV[0];
my $LanguageDir=$ARGV[1];
my $OutFileName=$ARGV[2];

my ($line,$keyLOC,$valLOC,$keyMASTER,$valMASTER) ;
my (%hashLOC, %hashMASTER);
#open folder
#open (FH,$LocFile) or die ("Can't open Loc.  file");
opendir (DIR,$LanguageDir);
my @files = grep (/\.properties$/i,readdir(DIR));


foreach my $file (@files) {
	print "Open file: $file\n";
	open (FH,$LanguageDir."\\".$file) or die ("can't open file [$LanguageDir\\$file]\n");
	#Read contents and put into hash
	while (<FH>) {
		chomp;
		my ($keyLOC,$valLOC) = split ("=");
		$hashLOC{$keyLOC} = $valLOC;
	}
	close FH;
}
close DIR;

#open master file
open (FHMASTER,$masterFile) or die ("Can't open master file");
#read into hash
while (<FHMASTER>) {
	chomp;
	my ($keyMASTER,$valMASTER) = split ("=");
	$hashMASTER{$keyMASTER} = $valMASTER;
}

#Open output file
my $outfilename = $OutFileName;
open (OUT, ">$outfilename") or die ("Can't open output file");

my $key;
my $value;
#Get updated values
for $key (sort keys %hashMASTER) {
	#replace &
	
	if ($hashLOC{$key} ne "") {
		$hashMASTER{$key} = $hashLOC{$key};
		$value = $hashMASTER{$key};
		$value=~s/&//;
	} else {
		$hashMASTER{$key} = "NULL";
	}
	print OUT "$key=$value\n";
}
close FH;
close OUT;
close FHMASTER;
print "DONE";




