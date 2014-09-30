__author__ = 'Netšajev'

from my_modul import *

def main():
    source = "pg3070.txt"
    target = "result.txt"

    try:
        f_target = open(target, "w")
        f_source = open(source, "r")
        print_top_words(find_top_words(count_words(process_file(f_source)), 3), f_target)
        f_source.close()
        f_target.close()
    except FileNotFoundError:
        print("File not found")


if __name__ == "__main__":
    main()