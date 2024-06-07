# s:str = "119 97 115 100 117 106 105 107 111 108"
s:str = "87 65 83 68 85 74 73 75 79 76"

chars = s.split(' ')
size = len(chars)

# for i in range(size, 100):
#     mySet = set(int(x)%i for x in chars)
#     if(size==len(mySet)):
#         print(i)
#         break
# => 16 (both)

table = [' ']*16
for x in chars:
    table[int(x)%16]=x

for i in table:
    print(i, end=", ")