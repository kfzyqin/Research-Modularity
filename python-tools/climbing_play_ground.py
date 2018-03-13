from ClimbingAnalyzer import  ClimbingAnalyzer
from file_processor import get_immediate_subdirectories

main_path = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/old-data-by-20-01-18/' \
               'soto-with-perturbation-recording/'

paths = get_immediate_subdirectories(main_path)

for a_path in paths[:1]:
    climbing_analyzer = ClimbingAnalyzer()
    climbing_analyzer.plot_blocks(climbing_analyzer.generate_blocks(a_path), True, a_path, 'cheating_paths.png')