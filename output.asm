	.data
newline:
	#Define a newline character
	.asciiz "\n"
varx:
	#Define a new variable and initialize to 0
	.word 0
vary:
	#Define a new variable and initialize to 0
	.word 0
varcount:
	#Define a new variable and initialize to 0
	.word 0
	.text 0x00400000
	.globl main
main:
	#main procedure
	li $v0, 2 #Set $v0 to a value
	la $t0, varx
	sw $v0, ($t0) #set variable x to $v0
	la $t0, varx #Load address of variable
	lw $v0, ($t0) #Load value of variable
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	li $v0, 1 #Set $v0 to a value
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	addu $v0, $t0, $v0
	#Binary operation complete
	la $t0, vary
	sw $v0, ($t0) #set variable y to $v0
	la $t0, varx #Load address of variable
	lw $v0, ($t0) #Load value of variable
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	la $t0, vary #Load address of variable
	lw $v0, ($t0) #Load value of variable
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	addu $v0, $t0, $v0
	#Binary operation complete
	la $t0, varx
	sw $v0, ($t0) #set variable x to $v0
	la $t0, varx #Load address of variable
	lw $v0, ($t0) #Load value of variable
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	la $t0, vary #Load address of variable
	lw $v0, ($t0) #Load value of variable
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	mul $v0, $t0, $v0
	#Binary operation complete
	move $a0, $v0 #Move value into argument register
	li $v0, 1 #Prepare for printing
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	la $t0, varx #Load address of variable
	lw $v0, ($t0) #Load value of variable
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	la $t0, vary #Load address of variable
	lw $v0, ($t0) #Load value of variable
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	ble $t0, $v0, elseif1
	#Condition for an if statement or while loop
	la $t0, varx #Load address of variable
	lw $v0, ($t0) #Load value of variable
	move $a0, $v0 #Move value into argument register
	li $v0, 1 #Prepare for printing
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	la $t0, vary #Load address of variable
	lw $v0, ($t0) #Load value of variable
	move $a0, $v0 #Move value into argument register
	li $v0, 1 #Prepare for printing
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif1
elseif1:
	#jump statement for the 1th else
endif1:
	#jump statement for the 1th if
	li $v0, 14 #Set $v0 to a value
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	li $v0, 14 #Set $v0 to a value
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	bne $t0, $v0, elseif2
	#Condition for an if statement or while loop
	li $v0, 14 #Set $v0 to a value
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	li $v0, 14 #Set $v0 to a value
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	beq $t0, $v0, elseif3
	#Condition for an if statement or while loop
	li $v0, 3 #Set $v0 to a value
	move $a0, $v0 #Move value into argument register
	li $v0, 1 #Prepare for printing
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif3
elseif3:
	#jump statement for the 3th else
endif3:
	#jump statement for the 3th if
	li $v0, 14 #Set $v0 to a value
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	li $v0, 14 #Set $v0 to a value
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	bgt $t0, $v0, elseif4
	#Condition for an if statement or while loop
	li $v0, 4 #Set $v0 to a value
	move $a0, $v0 #Move value into argument register
	li $v0, 1 #Prepare for printing
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif4
elseif4:
	#jump statement for the 4th else
endif4:
	#jump statement for the 4th if
	j endif2
elseif2:
	#jump statement for the 2th else
endif2:
	#jump statement for the 2th if
	li $v0, 15 #Set $v0 to a value
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	li $v0, 14 #Set $v0 to a value
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	ble $t0, $v0, elseif5
	#Condition for an if statement or while loop
	li $v0, 5 #Set $v0 to a value
	move $a0, $v0 #Move value into argument register
	li $v0, 1 #Prepare for printing
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif5
elseif5:
	#jump statement for the 5th else
endif5:
	#jump statement for the 5th if
	li $v0, 1 #Set $v0 to a value
	la $t0, varcount
	sw $v0, ($t0) #set variable count to $v0
while6:
	#jump statement for the 6th while
	la $t0, varcount #Load address of variable
	lw $v0, ($t0) #Load value of variable
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	li $v0, 15 #Set $v0 to a value
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	bgt $t0, $v0, endwhile6
	#Condition for an if statement or while loop
	la $t0, varcount #Load address of variable
	lw $v0, ($t0) #Load value of variable
	move $a0, $v0 #Move value into argument register
	li $v0, 1 #Prepare for printing
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	la $t0, varcount #Load address of variable
	lw $v0, ($t0) #Load value of variable
	subu $sp $sp 4
	sw $v0 ($sp) #push onto the stack
	li $v0, 1 #Set $v0 to a value
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off the stack
	addu $v0, $t0, $v0
	#Binary operation complete
	la $t0, varcount
	sw $v0, ($t0) #set variable count to $v0
	j while6 #loop to the beginning of the while
endwhile6:
	#empty end of the while loop
	li $v0, 10 #End program
	syscall
