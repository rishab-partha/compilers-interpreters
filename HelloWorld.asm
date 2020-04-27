# This program prints hello world.
# @author Anu Datar
# @version 04.13.2020

	.data
# Symbol msg represents the string data "Hello World!\n".
msg:
	.asciiz "Hello World!\n" # An ascii message

	.text 0x00400000
	.globl main

# Symbol main prints "Hello World!" to the terminal.
main:
	li $v0, 4    # Load 4 into $v0
	la $a0, msg  # Load address of msg into $a0
	syscall      # Prints a string from $a0
	li $v0, 10   # Load 10 into $v0
	syscall      # Exits

