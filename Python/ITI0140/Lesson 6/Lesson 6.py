__author__ = 'Netšajev'

import fstat  # import statistics module


fstat.print_stats(fstat.analyze('carcosa.txt'), 10)
print("-"*10)
fstat.print_stats(fstat.analyze('http://www.lambda.ee/w/images/d/d9/Frankenstein.txt'), 5)
help(fstat) # must display overview of the module and functions and usage info