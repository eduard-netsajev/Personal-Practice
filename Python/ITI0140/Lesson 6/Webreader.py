"""
Module for reading files over HTTP protocol.
"""
import urllib.request as UL


def read_url(s):
    """
    Reads URL using HTTP.

    Args:
    s -- URL
    Returns:
    string
    """
    try:
        return byte_literal_list_to_string(UL.urlopen(s).readlines())
    except UL.HTTPError as err:
        print("HTTP ERROR:", err)
        return None
    except UL.URLError as err:
        print("ERROR:", err)
        return None
    except:
        print("UNKNOWN ERROR!")
        return None
    #finally:
     #   save_data() # save unsaved data
     #   print("Goodbye world...")


def byte_literal_list_to_string(l):
    """
    Converts byte literal list to string

    Args:
    l -- byte literal list
    Returns:
    string
    """
    s = ""
    for line in l:
        s += line.decode("utf-8")
    return s


def main():
    print("hello")

if __name__ == "__main__":
    main()