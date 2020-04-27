# Symbol main and the program add up 2 and 3 before terminating the program.
# @author Anu Datar
# @version 04.13.2020
	.data 
	.text 0x00400000
	.globl main

main:
	li $t0, 2	   # load 2 into $t0
	li $t1, 3	   # load 3 into $t1
	addu $t2, $t0, $t1 # store $t0 + $t1 in $t2
	li $v0, 10
	syscall
