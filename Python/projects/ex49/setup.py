try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

config = {
    'description': 'My Project',
    'author': 'Eduard Netsajev',
    'url': 'http://github.com/eduard-netsajev',
    'download_url': 'Where to download it.',
    'author_email': 'themediaisajoke@gmail.com',
    'version': '0.1',
    'install_requires': ['nose'],
    'packages': ['NAME'],
    'scripts': [],
    'name': 'projectname'
}

setup(**config)
