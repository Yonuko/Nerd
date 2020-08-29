.data
test: .word 0
string_0: .asciiz "test"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall
.data
test: .word 0
string_0: .asciiz "test"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall
.data
test: .word 0
string_0: .asciiz "test"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing


li $v0, 4
la $a0, string_2
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?
"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing


li $v0, 4
la $a0, string_2
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?
"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing


li $v0, 4
la $a0, string_2
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?
"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing


li $v0, 4
la $a0, string_2
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?
"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing


li $v0, 4
la $a0, string_2
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?
"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing


li $v0, 4
la $a0, string_2
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?
"
string_3: .asciiz "test"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing




li $v0, 4
la $a0, string_3
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "Is my print working ?
"
string_3: .asciiz "test"
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing




li $v0, 4
la $a0, string_3
syscall
.data
test: .word 0
string_0: .asciiz "test
"
testing: .asciiz ""
string_1: .asciiz "I am testing"
string_2: .asciiz "is my print working ? "
.text


li $t0, 4

li $t1, 5

add $t0, $t0, $t1

sw $t0, test
lw $t0, test

li $t1, 15

lw $t2, test
add $t2, $t2, $t1
sw $t2, test
lw $t1, test

li $t2, 2

sub $t3, $t3, $t2
sw $t3, test
lw $t2, test

li $t3, 4

lw $t4, test
mul $t4, $t4, $t3
sw $t4, test
lw $t3, test

li $t4, 2

lw $t5, test
div $t5, $t5, $t4
sw $t5, test
lw $t4, test

lw $t5, test
addi $t5, $t5, 1
sw $t5, test
lw $t4, test

lw $t5, test
subi $t5, $t5, 1
sw $t5, test
lw $t4, test


li $v0, 4
la $a0, string_0
syscall



sw $t4, testing
la $t4, testing


la $t5, testing


li $v0, 4
la $a0, string_2
syscall
