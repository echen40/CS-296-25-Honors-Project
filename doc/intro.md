# Introduction to adventure

TODO: write [great documentation](http://jacobian.org/writing/what-to-write/)

Summary:
This is a text adventure game that consists of five levels set in an ancient temple. 
The player's goal is to escape the temple and return back to the human world. 

Map:

	north
          |
  west -- + -- east
          |
	south

(Labirynth_00)--(Labirynth_01)--(Labirynth_02)--(Labirynth_03)         (Hell)    (Heaven)
    |                 |               |               |			   \      /
(Labirynth_10)--(Labirynth_11)  (Labirynth_12)--(Labirynth_13)           (Great_Hall)
    |                                                 |                        |
(Labirynth_20)--(Labirynth_21)  (Labirynth_22)--(Labirynth_23)           (Music_Hall)--(Bridge)--(Observatory)--(Garden)
    |                 |               |               |                        |
(Labirynth_30)--(Labirynth_31)--(Labirynth_32)--(Labirynth_33)--(Forge)--(Trophy_Hall)--(Shrine)
									       |
					          (Library)--(Corridor)--(Gallery_Hall)--(Tunnel)--(Cavern)
									       |
							       (Room_N)--(Entrance_Hall)--(Room_Z)
									       |
									 (Storage_Room)


Rooms: total 35       / States: total 49
  1.  Storage_Room:     1 state
  2.  Room_N:           2 states
  3.  Entrance_Hall:    2 states
  4.  Room_Z:           2 states
  5.  Library:          1 state
  6.  Corridor:         2 states
  7.  Gallery_Hall:     2 states
  8.  Tunnel:           2 states
  9.  Cavern:           1 state
  10. Forge:            1 state
  11. Trophy_Hall:      2 states
  12. Shrine:           2 states
  13. Labirynth_00:     1 state
  14. Labirynth_01:     1 state
  15. Labirynth_02:     1 state
  16. Labirynth_03:     1 state
  17. Labirynth_10:     1 state
  18. Labirynth_11:     1 state
  19. Labirynth_12:     1 state
  20. Labirynth_13:     1 state
  21. Labirynth_20:     1 state
  22. Labirynth_21:     1 state
  23. Labirynth_22:     1 state
  24. Labirynth_23:     1 state
  25. Labirynth_30:     1 state
  26. Labirynth_31:     1 state
  27. Labirynth_32:     1 state
  28. Labirynth_33:     1 state
  29. Music_Hall:       5 states
  30. Bridge:           1 state
  31. Observatory:      1 state
  32. Garden:           1 state
  33. Great_Hall:       3 states
  34. Hell:             1 state
  35. Heaven:           1 state


Actions (Commands): total 19 (not including aliases)
  1.  look 
  2.  north
  3.  south
  4.  east
  5.  west 
  6.  right
  7.  left
  8.  use
  9.  get
  10. unlock
  11. point
  12. ask
  13. give
  14. forge
  15. shoot
  16. answer
  17. read
  18. choose
  19. list
  20. n
  21. s
  22. e
  23. w
  24. r
  25. l
  26. go north
  27. go south
  28. go east
  29. go west
  30. go right
  31. go left

Items (Objects): total 15 / States: total 17
  1.  torch                 1 state
  2.  wand                  2 states
  3.  gold_key              1 state
  4.  silver_key            1 state
  5.  bomb                  2 states
  6.  large_key             1 state
  7.  small_key             1 state
  8.  emerald               1 state
  9.  sapphire              1 state
  10. ruby                  1 state
  11. rainbow_gem           1 state
  12. bow_and_arrow         1 state
  13. notebook              1 state
  14. scroll                1 state
  15. diary                 1 state


Solution (words)
Level 1: First obtain the wand from the storage room to the south. Then, go to Room Z to the east and point the wand at the front-left, 
	 front-right, back-left, and back-right pillars in that order (clue: Room "Z"). Get the gold_key. Then, go to Room N to the west and 		 point the wand at the front-left, back-left, back-right, and front-right statues in that order (clue: whispering "you" = "U"). Get 	 	 the silver_key. Go back to the Entrance Hall and unlock the doors. Go north to proceed to level two.
Level 2: First obtain the torch from Entrance Hall. Then, go east from the Gallery Hall to the Tunnel and use torch to burn the spider web 	 	 down. Go east to the Cavern and obtain the large key. After that, go back to the tunnel to get the bomb. Continue west to the 		 Corridor, light the bomb, and use it to destroy the boulder. Go west to the library to get the small_key. Return to the Gallery Hall 		 and unlock the door. Go north to proceed to level three.
Level 3: First, go east to the shrine. You will find a goblin asking for a rainbow gem in exchange for a power to kill the ogre. Then, 		 continue west to the Labirynth. Find the emerald, sapphire, and ruby gemstones at the inner corners of each two-by-two blocks of the 
	 labirynth. Go back to the forge and forge the three gemstones into a rainbow gem. Proceed back to the shrine and give the rainbow gem
	 to the goblin. Obtain the bow and arrow and go back to the trophy hall. Shoot the ogre and then go north to proceed to level four.
Level 4: Go up to the old man and ask. You will find that he won't let you pass until you answer three of his riddles. Ask again and answer 	 	 man. Ask again and answer school. Ask again and answer wind. Ask again and he will let you pass. Go north to proceed to level five.
	 (Optional: For a clue to the first riddle, go east to the bridge, obtain the notebook, and read the notebook. Notice the keyword 		 "Homo sapiens" = "man". For a clue to the second riddle, go east to the observatory, obtain the scroll, and read the scroll. Notice 		 the keyword "education" = "school". For a clue to the third riddle, go east again to the garden, obtain the diary, and read the 	  diary, Notice the keyword "breeze" = "wind".)
Level 5: Go right to escape the temple and return back to the human world to complete the game. 
	 (Optional: For an answer to which door to go without guessing, ask the two guardians and they will explain that one door leads to 		 the human world, the other door leads to an eternal abyss, one of the guardians always lies, and the other always tells the truth. 		 Ask again so that they will give you a chance to ask a single question out of two they have prepared for you. Choose any question 	    and they will always point to the left door. The one they point to is always the one you don't want to go. Now you know that the 		 left door leads to an eternal abyss and that the right door leads to the human world.)

Solution (commands): total 91
  1.  south
  2.  get wand
  3.  north
  4.  east
  5.  point wand 2
  6.  point wand 1
  7.  point wand 3 
  8.  point wand 4
  9.  get gold_key
  10. west
  11. west
  12. point wand 2
  13. point wand 3
  14. point wand 4
  15. point wand 1
  16. get silver_key
  17. east
  18. unlock
  19. get torch
  20. north
  21. east
  22. use torch
  23. east
  24. get large_key
  25. west
  26. get bomb
  27. west
  28. west
  29. light bomb 
  30. use bomb
  31. west
  32. get small_key 
  33. east
  34. east
  35. unlock
  36. north
  37. east
  38. ask
  39. west
  40. west
  41. west
  42. north
  43. north
  44. west
  45. get emerald
  46. north
  47. west
  48. south
  49. get sapphire
  50. west
  51. south
  52. east
  53. get ruby
  54. south
  55. east
  56. east
  57. east
  58. forge
  59. east
  60. east
  61. give rainbow_gem
  62. look
  63. get bow_and_arrow
  64. west
  65. shoot
  66. north
  67. ask
  68. east
  69. get notebook
  70. read notebook
  71. east
  72. get scroll
  73. read scroll
  74. east
  75. get diary
  76. read scroll
  77. west
  78. west
  79. west
  80. ask
  81. answer man
  82. ask
  83. answer school
  84. ask
  85. answer wind
  86. ask
  87. north
  88. ask
  89. ask
  90. choose 1
  91. right











