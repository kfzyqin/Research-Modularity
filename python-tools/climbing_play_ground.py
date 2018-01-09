from ClimbingAnalyzer import  ClimbingAnalyzer
from file_processor import get_immediate_subdirectories

main_path = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'change-to-tournament-selection/'

paths = get_immediate_subdirectories(main_path)

climbing_analyzer = ClimbingAnalyzer()

for a_path in paths:
    climbing_analyzer.plot_blocks(climbing_analyzer.generate_blocks(a_path), True, a_path, 'cheating_paths.png')