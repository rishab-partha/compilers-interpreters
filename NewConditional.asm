# This program takes in two integers from the terminal and then performs a simple conditional.
# If the two numbers are equal, the number is printed, but if they are unequal, their product
# is printed.
# @author Rishab Parthasarathy
# @version 04.15.2020

	.data
# Msg represents the string requesting an input of two numbers, "Input two numbers, pressing the enter key between the two: ".
msg:
	.asciiz "Input two numbers, pressing the enter key between the two: "

# Msg1 represents the string preparing for the output, "The output is: ".
msg1:
	.asciiz "The output is: "
	
	.text 0x00400000
	.globl main

# Symbol main takes in two integers from the terminal before storing them.
# Then the symbol uses a control structure to check if the two are equal.
# If they are not equal, main prints their product.
#
# @postcondition if the numbers are unequal, the product has been printed
main:
	li $v0, 4             # Load 4 to $v0
	la $a0, msg           # Load the address of msg to $a0
	syscall               # Print the message to the terminal
	li $v0, 5             # Load 5 to $v0
	syscall               # Read in the first integer from the terminal
	move $t0, $v0         # Move the value of the first int to $t0
	li $v0, 5             # Load 5 to $v0
	syscall               # Read in the second integer
	move $t1, $v0         # Move the value of the second int to $t1
	li $v0, 4             # Load 4 to $v0
	la $a0, msg1          # Load the address of msg1 to $a0
	syscall               # Print msg1 to the terminal
	beq $t0, $t1, equal   # Test whether $t0 == $t1
		mult $t0, $t1 # Multiply $t0 and $t1
		mflo $a0      # Move the product to $a0
		j after

# Equal moves $t0 to $a0 if $t0 == $t1.
# 
# @precondition $t1 == $t0
# @postcondition $a0 = $t0, which is the number
equal:
	move $a0, $t0         # Load $t0 to $a0 if $t1 == $t0

# After prints the proper output and ends the code.

# @postcondition the proper output has been printed and the code has terminated normally
after:
	li $v0, 1             # Load 1 to $v0
	syscall               # Print $a0, the output, to the terminal
	li $v0, 10            # Load 10 to $v0
	syscall               # Exit the program
		
	

	
