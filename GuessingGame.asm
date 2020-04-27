# Program GuessingGame denotes a program in assembly where a person provides an upper bound ub.
# Then, the program chooses a random number n such that 0 <= n < ub. Afterwards, the program
# queries the user for guesses, and every time the user guesses, the user is informed whether his
# guess is less than or greater than the random chosen number. If the user guesses the number exactly,
# the program prints a congratulatory message and exits.
# 
# @author Rishab Parthasarathy
# @version 04.15.2020
	
	.data
# Msgintro represents the message that welcomes the user and provides preliminary instructions for the choice of an upper bound.
msgintro:
	.asciiz "You are now playing a guessing game to guess a random number between 0 and your choice of an upper bound.\n"

# Msgbound represents the message that queries the user for their upper bound.
msgbound:
	.asciiz "Please input your upper bound. "

# Msgguess represents the message that queries the user for their guess.
msgguess:
	.asciiz "Please input your guess. "

# Msglow represents the message that informs the user that their guess was too low.
msglow:
	.asciiz "Your answer was too low.\n"

# Msghigh represents the message that informs the user that their guess was too high.
msghigh:
	.asciiz "Your answer was too high.\n"

# Msgcorrect represents the message that informs the user that they guessed correctly.
msgcorrect:
	.asciiz "You guessed correctly! The number was "	
	
	.text 0x00400000
	.globl main	
	
# Symbol main prints the introductory message and queries the user for their upper bound.
# Then, main generates a random number in the range for use in the guessing game.
#
# @postcondition the program has generated a random number in the player's desired range
main:
	li $v0, 4               # Load 4 into $v0
	la $a0, msgintro        # Load the intro address to $a0
	syscall			# Print the intro
	la $a0, msgbound        # Load the bounding statement to $a0
	syscall			# Print the bounding statement
	li $v0, 5		# Load 5 into $v0
	syscall			# Read the upper bound
	move $a1, $v0		# Move the value of the upper bound to $a1
	li $v0, 42		# Load 42 into $v0
	syscall			# Put a random number in the range in $a0
	move $t0, $a0		# Put the value of $a0 into $t0
	
# Symbol guessloop represents one iteration of the guessing loop. The loop queries
# the user for a guess, and then checks whether the user guessed correctly. In both
# cases, the code jumps to the appropriate section.
#
# @postcondition the code has jumped to the correct section depending on if the guess was correct
guessloop:
	li $v0, 4		# Load 4 into $v0
	la $a0, msgguess	# Load the guess message to $a0
	syscall			# Print the guess message
	li $v0, 5		# Load 5 into $v0
	syscall			# Read in the guess
	bne $v0, $t0, wrong     # Check whether the guesses match up
		j ending	# If the guesses match, end

# Symbol wrong represents what happens if the user guesses wrong. The symbol checks whether the 
# guess wsas too small or too large. If the guess was too small, wrong prints out the appropriate
# message and returns to the loop, and if the guess was too large, wrong jumps to the appropriate
# symbol.
# 
# @postcondition the user has either already been informed that his guess was too small and has
# 		returned to the main loop, or the user is in another symbol for large guesses
wrong:
	bgt $v0, $t0, greater   # Check whether $v0 > $t0
		la $a0, msglow  # If $v0 < $t0, load the message saying that the guess was low to $a0
		li $v0, 4       # Load 4 into $v0
		syscall         # Print the message saying that the guess was too small
		j guessloop     # Go back and execute the guessing loop once more

# Symbol greater represents what happens if the user guesses too big. The symbol prints out the
# appropriate message and returns to the main loop.
#
# @postcondition the appropriate message has been printed and the user has returned to the main loop
greater:
	la $a0, msghigh		# If $v0 > $t0, load the message saying that the guess was high to $a0
	li $v0, 4		# Lod 4 into $v0
	syscall			# Print the message saying that the guess was too big
	j guessloop		# Go back and execute the guessing loop once more

# Symbol ending represents what happens after the user guesses correctly. The symbol prints a congratulatory
# message with the correct number and then exits from the program. 
#
# @postcondition the program has congratulated the user and exited successfully
ending:
	li $v0, 4		# Load 4 into $v0
	la $a0, msgcorrect	# Load the correct message into $a0
	syscall			# Print the correct message
	li $v0, 1		# Load 1 into $v0
	move $a0, $t0		# Transfer the value of $t0 to $a0
	syscall			# Print the random number
	li $v0, 10		# Load 10 into $v0
	syscall			# end program

	
