	.data
newline:
	.asciiz "\n"
	.text 0x00400000
	.globl main

main:
	li $v0, 9
	li $a0, 100000
	syscall
	li $s1, 0
	move $s0, $v0
	li $a0, 7
	li $a1, 5
	li $a2, 4
	jal max3
	jal fprintln
	li $a0, 10
	jal fib
	jal fprintln
	li $a0, 11
	jal fact
	jal fprintln
	li $a0, 1000
	li $a1, 0
	jal newlistnode
	li $a0, 500523
	move $a1, $s0
	jal newlistnode
	move $a0, $s0
	add $a0, $a0, 8
	jal sumlist
	jal fprintln
	li $v0, 10
	syscall
fnewline:
	li $v0, 4
	la $a0, newline
	syscall
	jr $ra
fprint:
	move $a0, $v0
	li $v0, 1
	syscall
	jr $ra
fprintln:
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal fprint
	jal fnewline
	lw $ra, ($sp)
	addu $sp, $sp, 4
	jr $ra
	
max2:
	ble $a0, $a1, elseif1
		move $v0, $a0
		j endif1
elseif1:
	move $v0, $a1
endif1:
	jr $ra
	
max3:
	subu $sp, $sp, 4
	sw $ra, ($sp)
	subu $sp, $sp, 4
	sw $a2, ($sp)
	jal max2
	lw $a2, ($sp)
	addu $sp, $sp, 4
	lw $ra, ($sp)
	addu $sp, $sp, 4
	move $a0, $v0
	move $a1, $a2
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal max2
	lw $ra, ($sp)
	addu $sp, $sp, 4
	jr $ra

fact:
	beq $a0, 0, elseif2
		subu $sp, $sp, 4
		sw $ra, ($sp)
		subu $sp, $sp, 4
		sw $a0, ($sp)
		subu $a0, $a0, 1
		jal fact
		lw $a0, ($sp)
		addu $sp, $sp, 4
		lw $ra, ($sp)
		addu $sp, $sp, 4
		mul $v0, $a0, $v0
		j endif2
elseif2:
	li $v0, 1
endif2:
	jr $ra
	
fib:
	ble $a0, 1, elseif3
		subu $sp, $sp, 4
		sw $ra, ($sp)
		subu $sp, $sp, 4
		sw $a0, ($sp)
		subu $a0, $a0, 1
		jal fib
		lw $a0, ($sp)
		addu $sp, $sp, 4
		subu $sp, $sp, 4
		sw $a0, ($sp)
		subu $sp, $sp, 4
		sw $v0, ($sp)
		subu $a0, $a0, 2
		jal fib
		lw $t0, ($sp)
		addu $sp, $sp, 4
		lw $a0, ($sp)
		addu $sp, $sp, 4
		lw $ra, ($sp)
		addu $sp, $sp, 4
		addu $v0, $v0, $t0
		j endif3
elseif3:
	move $v0, $a0
endif3:
	jr $ra
newlistnode:
	add $v0, $s0, $s1
	sw $a0, ($v0)
	sw $a1, 4($v0)
	addi $s1, $s1, 8
	jr $ra
sumlist:
	beq $a0, 0, elseif4
		subu $sp, $sp, 4
		sw $ra, ($sp)
		subu $sp, $sp, 4
		sw $a0, ($sp)
		lw $a0, 4($a0)
		jal sumlist
		lw $a0, ($sp)
		addu $sp, $sp, 4
		lw $ra, ($sp)
		addu $sp, $sp, 4
		lw $t0, 0($a0)
		add $v0, $t0, $v0
		j endif4
elseif4:
	li $v0, 0
endif4:
	jr $ra
		
	
