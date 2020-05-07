	.data
newline:
	.asciiz "\n"
varcount:
	.word 0
	.text 0x00400000
	.globl main
main:
	li $v0, 1
	la $t0, varcount
	sw $v0, ($t0)
while1:
	la $t0, varcount
	lw $v0, ($t0)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0, 15
	lw $t0 ($sp)
	addu $sp $sp 4
	bgt $t0, $v0, endif1
	la $t0, varcount
	lw $v0, ($t0)
	move $a0, $v0
	li $v0, 1
	syscall
	la $a0 newline
	li $v0 4
	syscall
	la $t0, varcount
	lw $v0, ($t0)
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0, 1
	lw $t0 ($sp)
	addu $sp $sp 4
	addu $v0, $t0, $v0
	la $t0, varcount
	sw $v0, ($t0)
	j while1
endif1:
	li $v0, 10
	syscall
