.data

	initText: .asciiz "Leibniz: pi = 4*{1 - 1/3 + 1/5 - 1/7 + 1/9 - 1/11 + ...}\n"
	realAwnser: .asciiz "precis: pi=3.14159265358979\n"
	pi: .double 1.0

.text

	.globl end
	main:
		li $t0, 10 # Counter
		li $t1, 1000000 # Count of interation
		li $t2, 1 # Counter second loop
		la $t3, pi # Store pi address inside of t3
		li $v0, 4
		la $a0, initText
		syscall
		j loop
	loop:
		beq $t0, $t1, end
		# print current number
		j function
		mul $t0, $t0, 10
		j loop
	function:
		beq $t2, $t0, loop
		
		addi $t2, $t2, 1
		j function
	end:
		