# This program reads in an integer from the terminal and then prints out
# the square of the integer.
# @author Rishab Parthasarathy
# @version 04.13.2020

	.data
# Msg is the string that queries the user for data, "Please input an integer in the terminal."
msg:
	.asciiz "Please input an integer in the terminal. "

# Msg1 is the string that begins the statement printing the square, "The square of the number is: "
msg1:
	.asciiz "The square of the number is: "

	.text 0x00400000
	.globl main

# Symbol main reads in an integer from the terminal while printing messages
# to query the user. Then, the symbol squares the number taken in and assigns
# it to $a0 before printing the square out and terminating the program.
#
# @precondition There is no overflow
# @postcondition The program has printed the square and terminated
main:
	li $v0, 4      # Load 4 to $v0
	la $a0, msg    # Load the address of msg to $a0
	syscall	       # Print the value of $a0
	li $v0, 5      # Load 5 to $v0
	syscall        # Read an int from the terminal to $v0
	mult $v0, $v0  # Multiply $v0 by itself
	li $v0, 4      # Load 4 to $v0
	la $a0, msg1   # Load the address of msg1 to $a0
	syscall	       # Print the message msg1 to the terminal
	li $v0, 1      # Load 1 to $v0
	mflo $a0       # Assign the value of the product to $a0
	syscall        # Print the value of $a0 to the terminal
	li $v0, 10     # Load 10 to $v0
	syscall        # exit the program
	
