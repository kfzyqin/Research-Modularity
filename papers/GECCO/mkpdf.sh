# ./mkpdf.sh creates the pdf for an article. This version may work for ACM, but
# confirmation is needed (note that at least until 2015, pdflatex was not
# compatible with IEEE requirements)

pdflatex mod
pdflatex mod
bibtex mod
pdflatex mod
pdflatex mod
# Don't need to go via dvi because using pdflatex???
# dvips -t letter -Ppdf mod.dvi -o
# ps2pdf -sPAPERSIZE=letter mod.ps

