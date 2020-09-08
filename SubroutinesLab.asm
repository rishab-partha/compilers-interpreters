# Program SubroutinesLab defines a few simple subroutines. First, the program has
# the subroutines max2 and max3, which calculate the maximum of 2 or 3 elements,
# respectively. Apart from these two subroutines, the program also contains recursive
# subroutines for calculating factorial, fibonacci, and defining/calculating the sum
# of linked lists. In order to facilitate the testing of these subroutines, the program
# also contains subroutines for printing to the console.
#
# @author Rishab Parthasarathy
# @version 05.15.2020

	.data
# Label newline stores the ascii value of a newline character
newline:
	.asciiz "\n"

	.text 0x00400000
	.globl main

# Label main tests all the subroutines within the program. In essence, main creates the
# heap space and calls max3(7, 5, 4), fib(10), fact(11), newlistnode(1000, 0), 
# newlistnode(500523, node0) and sumlist(node1). Then, it prints the results of these
# calls separated by lines.
#
# @postcondition The program has exited normally with correct output.
main:
	li $v0, 9
	li $a0, 100000
	syscall            # Create 100000 bytes of heap space for ListNodes
	li $s1, 0
	move $s0, $v0      # Move heap address to $s0 and initialize $s1 as depth of stack
	li $a0, 7
	li $a1, 5
	li $a2, 4
	jal max3           # Set parameters and call max3(7, 5, 4)
	jal fprintln       # Print the return value of max3 with a newline
	li $a0, 10
	jal fib            # Set parameters and call fib(10)
	jal fprintln       # Print the return value of fib with a newline
	li $a0, 11
	jal fact           # Set parameters and call fact(11)
	jal fprintln       # Print the return value of fact with a newline
	li $a0, 1000
	li $a1, 0
	jal newlistnode    # Set parameters and call newlistnode(1000, 0) to create node0
	li $a0, 500523
	move $a1, $s0
	jal newlistnode    # Set parameters and call newlistnode(500523, node0) to create node1
	move $a0, $s0
	add $a0, $a0, 8
	jal sumlist        # Set parameters and call sumlist(node1)
	jal fprintln       # Print the return value of sumlist with a newline
	li $v0, 10
	syscall            # End the program normally

# Subroutine fnewline prints a newline to the console and returns to the previous location.
# 
# @postcondition A newline is printed to the console and the subroutine returns to the previous
#                location
fnewline:
	li $v0, 4
	la $a0, newline
	syscall            # Print a newline
	jr $ra             # Return to the previous location
	
# Subroutine fnewline prints the value of $v0 to the console and returns to the previous location.
# 
# @postcondition $v0 is printed to the console and the subroutine returns to the previous
#                location
fprint:
	move $a0, $v0
	li $v0, 1
	syscall            # Print the value that was once stored in $v0
	jr $ra             # Return to the previous location
	
# Subroutine fnewline prints the value of $v0 to the console with a newline and returns
# to the previous location.
# 
# @postcondition $v0 an a newline are printed to the console and the subroutine returns
#                to the previous location
fprintln:
	subu $sp, $sp, 4
	sw $ra, ($sp)      # Push $ra onto the stack
	jal fprint         # Print the value of $v0
	jal fnewline       # Print a newline
	lw $ra, ($sp)
	addu $sp, $sp, 4   # Pop $ra off the stack
	jr $ra             # Return to the previous location

# Subroutine max2 finds the maximum of the two values in $a0 and $a1 and moves that value
# into $v0. After doing this, it returns to the previous location.
#
# @param $a0 The first number
# @param $a1 The second number
# @postcondition Return to the previous location
# @return The maximum of the values in $a0 and $a1, which is stored in $v0
max2:
	ble $a0, $a1, elseif1    # If $a0 <= $a1, jump to elseif1
		move $v0, $a0    # If $a0 > $a1, set $v0 = $a0
		j endif1         # Jump to the end of the if
		
# Label elseif1 sets $v0 = $a1 if $a1 >= $a0.
#
# @postcondition If $a1 >= $a0, $v0 contains the value of $a1
elseif1:
	move $v0, $a1

# Label endif1 returns to the previous location by return jumping.
#
# @postcondition The program is at the previous location
endif1:
	jr $ra
	
# Subroutine max3 finds the maximum of the three values in $a0, $a1, and $a2 and moves that value
# into $v0. After doing this, it returns to the previous location.
#
# @param $a0 The first number
# @param $a1 The second number
# @param $a2 The third number
# @postcondition Return to the previous location
# @return The maximum of the values in $a0, $a1, and $a2, which is stored in $v0
max3:
	subu $sp, $sp, 4
	sw $ra, ($sp)      # Push $ra onto the stack
	subu $sp, $sp, 4
	sw $a2, ($sp)      # Push $a2 onto the stack
	jal max2           # Call max2 to put the max of $a0 and $a1 in $v0
	lw $a2, ($sp)
	addu $sp, $sp, 4   # Pop $a2 off the stack
	lw $ra, ($sp)
	addu $sp, $sp, 4   # Pop $ra off the stack
	move $a0, $v0 
	move $a1, $a2      # Move $v0 and $a2 into $a0 and $a1, respectively
	subu $sp, $sp, 4
	sw $ra, ($sp)      # Push $ra onto the stack
	jal max2           # Call max2 to put the max of $a0, $a1, and $a2 in $v0
	lw $ra, ($sp)
	addu $sp, $sp, 4   # Pop $ra off the stack
	jr $ra             # Jump back to the previous location

# Subroutine fact recursively finds the value of n factorial, where n is the value in $a0.
# To do this, it uses the formula n! = n * (n - 1)! and 0! = 1. After doing this, it returns
# to the previous location.
#
# @param $a0 The number to find factorial of
# @postcondition Return to the previous location
# @return The factorial of $a0, which is stored in $v0
fact:
	beq $a0, 0, elseif2          # If $a0 == 0, jump to elseif2
		subu $sp, $sp, 4     
		sw $ra, ($sp)        # Push $ra onto the stack
		subu $sp, $sp, 4
		sw $a0, ($sp)        # Push $a0 onto the stack
		subu $a0, $a0, 1     # Turn $a0 into $a0 - 1
		jal fact             # Put ($a0 - 1)! into $v0
		lw $a0, ($sp)
		addu $sp, $sp, 4     # Pop $a0 off the stack
		lw $ra, ($sp)
		addu $sp, $sp, 4     # Pop $ra off the stack
		mul $v0, $a0, $v0    # Put ($a0)! = $a0 * ($a0 - 1)! in $v0
		j endif2             # Jump to endif2

# Label elseif2 sets $v0 = 1 if $a0 = 0.
#
# @postcondition If $a0 = 0, $v0 contains the value 1
elseif2:
	li $v0, 1
	
# Label endif2 returns to the previous location by return jumping.
#
# @postcondition The program is at the previous location
endif2:
	jr $ra
	
# Subroutine fib recursively finds the value of F(n), where n is the value in $a0 and F(n) is the
# nth Fibonacci number. To do this, it uses the formula F(n) = F(n - 1) + F(n - 2) if n > 1 and 
# F(n) = n if n <= 1. After doing this, it returns to the previous location.
#
# @param $a0 The number to find the nth Fibonacci number
# @postcondition Return to the previous location
# @return The $a0th Fibonacci number, which is stored in $v0
fib:
	ble $a0, 1, elseif3          # If $a0 <= 1, jump to elseif3
		subu $sp, $sp, 4        
		sw $ra, ($sp)        # Push $ra onto the stack
		subu $sp, $sp, 4
		sw $a0, ($sp)        # Push $a0 onto the stack
		subu $a0, $a0, 1     # Turn $a0 into $a0 - 1
		jal fib              # Store the value of F($a0 - 1) in $v0
		lw $a0, ($sp)
		addu $sp, $sp, 4     # Load/Pop $a0 off the stack
		subu $sp, $sp, 4
		sw $a0, ($sp)        # Push $a0 onto the stack
		subu $sp, $sp, 4
		sw $v0, ($sp)        # Push $v0 or F($a0 - 1) onto the stack
		subu $a0, $a0, 2     # Turn $a0 into $a0 - 2
		jal fib              # Store the value of F($a0 - 2) in $v0
		lw $t0, ($sp)
		addu $sp, $sp, 4     # Pop $t0 or F($a0 - 1) off the stack
		lw $a0, ($sp)
		addu $sp, $sp, 4     # Pop $a0 off the stack
		lw $ra, ($sp)
		addu $sp, $sp, 4     # Pop $ra off the stack
		addu $v0, $v0, $t0   # Put F($a0) in $v0 using the formula F($a0) = F($a0 - 1) + F($a0 - 2)
		j endif3             # Jump to endif3
		
# Label elseif3 sets $v0 = $a0 if $a0 <= 1.
#
# @postcondition If $a0 <= 1, $v0 contains the value $a0
elseif3:
	move $v0, $a0
	
# Label endif3 returns to the previous location by return jumping.
#
# @postcondition The program is at the previous location
endif3:
	jr $ra
	
# Subroutine newlistnode creates a new ListNode(val, next), where val is the value stored within
# the ListNode and next is the next ListNode in the linked list. This ListNode is stored on the
# next location on the heap, with the first 4 bytes being the value and the next 4 bytes being the
# address of the next ListNode. After doing this, it returns to the previous location.
#
# @param $a0 The value to be stored in the ListNode
# @param $a1 The address of the next ListNode in the linked list
# @postcondition Return to the previous location and the ListNode is defined on the next 8 bytes
#                on the heap.
newlistnode:
	add $t0, $s0, $s1     # Put the current open location on the heap in $t0 by adding $s0 and $s1
	sw $a0, ($t0)         # Store the value stored in the ListNode in the first 4 available bytes
	sw $a1, 4($t0)        # Store the next ListNode in the linked list in the next 4 available bytes
	addi $s1, $s1, 8      # Move to the next open location on the heap by adding 8 to $s1
	jr $ra                # Jump back to the previous location

# Subroutine sumlist recursively finds the sum of the values of the linked list starting with the
# the ListNode whose address is in $a0. To do this, if the address of the next ListNode is 0,
# which represents null, the subroutine puts 0 in $v0. Otherwise, the subroutine puts the sum
# of the value of the ListNode with the recursively calculated sum of the next ListNode into $v0. 
# After doing this, it returns to the previous location.
#
# @param $a0 The address of the first ListNode in the linked list
# @postcondition Return to the previous location
# @return The sum of all the values in the linked list
sumlist:
	beq $a0, 0, elseif4          # If $a0 == 0, jump to elseif4
		subu $sp, $sp, 4
		sw $ra, ($sp)        # Push $ra onto the stack
		subu $sp, $sp, 4
		sw $a0, ($sp)        # Push $a0 onto the stack
		lw $a0, 4($a0)       # Load the value of the next ListNode into $a0
		jal sumlist          # Recursively put the sum of the rest of the linked list into $v0
		lw $a0, ($sp)
		addu $sp, $sp, 4     # Pop $a0 off the stack
		lw $ra, ($sp)
		addu $sp, $sp, 4     # Pop $ra off the stack
		lw $t0, 0($a0)       # Load the value of the ListNode at $a0 into $t0
		add $v0, $t0, $v0    # Put the sum of the values of the list into $v0 by adding $t0 and $v0
		j endif4             # Jump to endif4
		
# Label elseif4 sets $v0 = 0 if $a0 = 0, which represents null.
#
# @postcondition If $a0 = 0, or null, $v0 contains the value 0
elseif4:
	li $v0, 0
	
# Label endif3 returns to the previous location by return jumping.
#
# @postcondition The program is at the previous location
endif4:
	jr $ra
		
	
