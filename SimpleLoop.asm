# This program implements a simple loop that prints out the even numbers from
# 20 to 2 in backwards order.
#
# @author Rishab Parthasarathy
# @version 04.13.2020
	.data
# Newline represents the string newline in order to separate the numbers.
newline:
	.asciiz "\n"
	
	.text 0x00400000
	.globl main

# Symbol main prepares the loop by starting $t0 at 20 and 
# jumps to the loop.
#
# @postcondition the loop has begun at 20	
main:
	li $t0, 20                 # Load 20 to $t0
	j looptest		  # Jump to the code at looptest

# Looptest implements the loop by printing $t0 and a newline every
# iteration before jumping back to the beginning of itself while 
# the value of $t0 > 0.
#
# @postcondition The loop has performed the appropriate behavior
looptest:
	ble $t0, 0, end          # Checks whether $t0 <= 0
		li $v0, 1         # Load 1 to $v0
		move $a0, $t0     # Move the value of $t0 to $a0
		syscall           # Print the value of $a0
		li $v0, 4         # Load 4 to $v0
		la $a0, newline	  # Load the newline to $a0
		syscall           # Print the newline
		addi $t0, $t0, -2 # Decrement $t0 by 2
	j looptest                # While the code runs, repeat the loop

# End implements the end of the loop by terminating the program.
#
# @postcondition the program has terminated
end:
	li $v0, 10                # Load 10 to $v0
	syscall                   # Exit from the program
	

