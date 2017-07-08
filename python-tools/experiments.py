import pandas as pd
import os
import re



path_1 = "/Users/zhenyueqin/Downloads/diploid-grn-2-target-10-matrix/"

def get_immediate_subdirectories(a_dir):
    return [name for name in os.listdir(a_dir)
        if os.path.isdir(os.path.join(a_dir, name))]

print get_immediate_subdirectories(path_1)