import ast

from ClimbingAnalyzer import  ClimbingAnalyzer
import sys

working_path = sys.argv[1]
paths = ast.literal_eval(sys.argv[2])

climbing_analyzer = ClimbingAnalyzer()
climbing_analyzer.plot_blocks(climbing_analyzer.generate_blocks(paths), True, working_path, 'cheating_paths.png')
