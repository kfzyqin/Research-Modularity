import ast
from ClimbingAnalyzer import ClimbingAnalyzer
import sys

working_path = sys.argv[1]


original_fitness_paths = ast.literal_eval(sys.argv[2])

print(original_fitness_paths)

climbing_analyzer = ClimbingAnalyzer()
climbing_analyzer.plot_blocks(climbing_analyzer.generate_blocks(original_fitness_paths), True, working_path,
                              'original_fitness_cheating_paths.png')


# climbing_analyzer.plot_blocks(climbing_analyzer.generate_blocks(another_fitness_paths), True, working_path,
#                               'another_fitness_cheating_paths.png')
