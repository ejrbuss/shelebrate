``````
# Commands
echo "Hello World" # -> hello world

mkdir "My Files"
cp "~/Some File" "My Files"
ls "My Files" # -> Some File

# Wildcards
ls *.jpg
ls **.log

# Pipes and redirections
grep fish < /etc/shells > ~/output.txt 2> ~/errors.txt

# Variables
$name = "Mister Noodle"
mkdir $name
ls # -> Mister Noodle

export $name
delete $name

$my_list = [
    [ item1 item2 ]
    [ 12 4 ]
]

$my_list 0  # -> [ item1 item2 ]
$my_list -1 # -> [ 12 4 ]

for $PATH { $val -> 
    echo "entry: $val"
}

$a = [ 1 2 ]
$1 = [ a b ]
for $a $1 { $1$2 }                     # -> [ 1a 2a 1b 2b ]
foldl { $1$2 } "" (for $a $1 { $1$2 }) # -> 1a2a1b2b

echo "In $(pwd), running $(uname)"

printf "%s\n" ...(pkg-config --libs gio-2.0)

echo fish; echo chips
```