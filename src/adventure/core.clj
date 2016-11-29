(ns adventure.core
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str])
  (:gen-class))

(def the-map
  {;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Level 1
    :Entrance_Hall {:desc {:1 "Exits exist in all four directions. However, only the north is locked. I wonder where they lead to..."
			   :2 "Torches decorate the hall and on the walls, the shadow dances."}
           :title "in the Entrance Hall"
           :dir {:1 {:east :Room_Z
                     :west :Room_N
		     :south :Storage_Room}
                 :2 {:east :Room_Z
                     :west :Room_N
		     :south :Storage_Room
                     :north :Gallery_Hall}}
           :contents {:1 #{:torch}
		      :2 #{:torch}}
           :state :1}
   :Room_Z {:desc {:1 "Surrounding you stand four great pillars with some ancient script written on it. The pillars are numbered 1-2-3-4 in the order of front-right, front-left, back-left, and back-right. In the middle lies a treasure chest that holds a gold_key. Yet, you seem to be blocked by an invisible barrier."
		   :2 "Surrounding you stand four great pillars with some ancient script written on it, and in the middle lies an open treasure chest containing a gold_key."}
              :title "in Room Z"
              :dir {:1 {:west :Entrance_Hall}
                    :2 {:west :Entrance_Hall}}
              :contents {:1 #{}
		         :2 #{:gold_key}}
              :state :1}
   :Room_N {:desc {:1 "In front of you are four statues, all of which are mythical beasts. The statues are numbered 1-2-3-4 in the order of front-right, front-left, back-left, and back-right. At the center rests a troll, protecting what seems like a silver_key. You continuously hear a whisper: you...you...you....."
			   :2 "In front of you are four statues each of which represents a different mythical beast. At the center is a silver_key."}
              :title "in Room N"
              :dir {:1 {:east :Entrance_Hall}
                    :2 {:east :Entrance_Hall}}
              :contents {:1 #{}
		         :2 #{:silver_key}}
              :state :1}
   :Storage_Room {:desc {:1 "Though dusty and old, it seems to store some magical tools which could be of some use. Look, a wand!"}
              :title "in the Storage Room"
              :dir {:1 {:north :Entrance_Hall}
                    :2 {:north :Entrance_Hall}}
              :contents {:1 #{:wand}}
              :state :1}
   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Level 2
   :Gallery_Hall {:desc {:1 "Exits exist in all four directions. Yet again, the north is locked."
                         :2 "Paintings fill the hall, giving off a vibrant, lively atmosphere."}
              :title "in the Gallery Hall"
              :dir {:1 {:south :Entrance_Hall
                        :east :Tunnel
                        :west :Corridor}
                    :2 {:south :Entrance_Hall
                        :east :Tunnel
                        :west :Corridor
                        :north :Trophy_Hall}}
              :contents {:1 #{}
			 :2 #{}}
              :state :1}
   :Tunnel {:desc {:1 "Darkness swarms inside the tunnel. In front of you lies a spider web that blocks your way east."
                   :2 "Taking a closer look, there is a bag of bombs in the corner. What could it be used for?"}
              :title "in the Tunnel"
              :dir {:1 {:west :Gallery_Hall}
                    :2 {:east :Cavern
                       :west :Gallery_Hall}}
              :contents {:1 #{}
			 :2 #{:bomb}}
              :state :1}
   :Corridor {:desc {:1 "In front of you is a boulder. Looks like a dead end."
                     :2 "There is nothing but an eerie quietness."}
              :title "in the Corridor"
              :dir {:1 {:east :Gallery_Hall}
                    :2 {:east :Gallery_Hall
                       :west :Library}}
              :contents {:1 #{}
			 :2 #{}}
              :state :1}
   :Cavern {:desc {:1 "Following the walls through the darkness, you feel a large_key."}
              :title "in the Cavern"
              :dir {:1 {:west :Tunnel}}
              :contents {:1 #{:large_key}}
              :state :1}
   :Library {:desc {:1 "Flipping through the books, you find a small_key."}
              :title "in the Library"
              :dir {:1 {:east :Corridor}}
              :contents {:1 #{:small_key}}
              :state :1}
   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Level 3
   :Trophy_Hall {:desc {:1 "Exits exist in all four directions. This time, an ogre blocks the way north. You can't get near him."
                        :2 "In front of you lies the dead ogre. I never told you that those trophies are the heads of those who came before you, did I? You can probably guess what happened."}
              :title "in the Trophy Hall"
              :dir {:1 {:east :Shrine
                       :west :Forge
                       :south :Gallery_Hall}
                    :2 {:east :Shrine
                       :west :Forge
                       :south :Gallery_Hall
                       :north :Music_Hall}}
              :contents {:1 #{}
			 :2 #{}}
              :state :1}
   :Shrine {:desc {:1 "A goblin sits at the center of the shrine. He stares at you."
                   :2 "A goblin sits at the center of the shrine. Right beside him materializes a bow_and_arrow."}
              :title "in the Shrine"
              :dir {:1 {:west :Trophy_Hall}
		    :2 {:west :Trophy_Hall}}
              :contents {:1 #{}
			 :2 #{:bow_and_arrow}}
	      :state :1}
   :Forge {:desc {:1 "What a hot, dimmly lighted place. It's almost like an inferno."}
              :title "in the Forge"
              :dir {:1 {:east :Trophy_Hall
                       :west :Labirynth_33}}
              :contents {:1 #{}}
              :state :1}
   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Labirynth
   :Labirynth_00 {:desc {:1 "You are definitely lost."}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_01
                        :south :Labirynth_10}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_01 {:desc {:1 "I think you are lost."}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_02
                       :west :Labirynth_00
                       :south :Labirynth_11}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_02 {:desc {:1 "Where are you?"}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_03
                       :west :Labirynth_01
                       :south :Labirynth_12}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_03 {:desc {:1 "You might have made a wrong mistake."}
              :title "in the Labirynth"
              :dir {:1 {:west :Labirynth_02
                       :south :Labirynth_13}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_10 {:desc {:1 "I wonder where this is."}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_11
                       :north :Labirynth_00
                       :south :Labirynth_20}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_11 {:desc {:1 "A sapphire glows in the cold blue light"}
              :title "in the Labirynth"
              :dir {:1 {:west :Labirynth_10
                       :north :Labirynth_01}}
              :contents {:1 #{:sapphire}}
              :state :1}
   :Labirynth_12 {:desc {:1 "An emerald radiates a lively green light"}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_13
                       :north :Labirynth_02}}
              :contents {:1 #{:emerald}}
              :state :1}
   :Labirynth_13 {:desc {:1 "Are you sure you can get out?"}
              :title "in the Labirynth"
              :dir {:1 {:west :Labirynth_12
                       :north :Labirynth_03
                       :south :Labirynth_23}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_20 {:desc {:1 "You're going to be stuck here for a while."}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_21
                       :north :Labirynth_10
                       :south :Labirynth_30}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_21 {:desc {:1 "A ruby flickers a warm red light."}
              :title "in the Labirynth"
              :dir {:1 {:west :Labirynth_20
                       :south :Labirynth_31}}
              :contents {:1 #{:ruby}}
              :state :1}
   :Labirynth_22 {:desc {:1 "I bet you've already forgot you're path"}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_23
                       :south :Labirynth_32}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_23 {:desc {:1 "Good luck coming out!"}
              :title "in the Labirynth"
              :dir {:1 {:west :Labirynth_22
                       :north :Labirynth_13
                       :south :Labirynth_33}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_30 {:desc {:1 "Don't give up!"}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_31
                       :north :Labirynth_20}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_31 {:desc {:1 "Why did you do this to yourself?"}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_32
                       :west :Labirynth_30
                       :north :Labirynth_21}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_32 {:desc {:1 "Hmm... How long will this take?"}
              :title "in the Labirynth"
              :dir {:1 {:east :Labirynth_33
                       :west :Labirynth_31
                       :north :Labirynth_22}}
              :contents {:1 #{}}
              :state :1}
   :Labirynth_33 {:desc {:1 "First step into the darkness. How does it feel?"}
              :title "in the Labirynth"
              :dir {:1 {:east :Forge
                       :west :Labirynth_32
                       :north :Labirynth_23}}
              :contents {:1 #{}}
              :state :1}
   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Level 4
   :Music_Hall {:desc {:1 "Exits exist in all four directions, but the west. To the north stands an old man who does not let you pass."
		       :2 "The hall sings a melodious tune."
		       :3 "The hall sings a melodious tune."
		       :4 "The hall sings a melodious tune."
		       :5 "The harmonious melody makes you want to stay here forever."}
              :title "in the Music Hall"
              :dir {:1 {:south :Trophy_Hall
		        :east :Bridge}
                    :2 {:south :Trophy_Hall
		        :east :Bridge}
                    :3 {:south :Trophy_Hall
		        :east :Bridge}
                    :4 {:south :Trophy_Hall
		        :east :Bridge}
                    :5 {:north :Great_Hall
		        :south :Trophy_Hall
		        :east :Bridge}}
              :contents {:1 #{}
			 :2 #{}
			 :3 #{}
			 :4 #{}
			 :5 #{}}
              :state :1}
   :Bridge {:desc {:1 "In the middle of the bridge is a notebook. What could it contain?"}
              :title "on the Bridge"
              :dir {:1 {:east :Observatory
		        :west :Music_Hall}}
              :contents {:1 #{:notebook}}
              :state :1}
   :Observatory {:desc {:1 "Up at the observatory, you see nothing but thick layers of mist. However, you do find a scroll."}
              :title "in the Observatory"
              :dir {:1 {:east :Garden
		                    :west :Bridge}}
              :contents {:1 #{:scroll}}
              :state :1}
   :Garden {:desc {:1 "Flora and fauna strives around you. Yet, you could never reach them. The only thing that you could grasp is a diary lying on the ground. Everything seems like an ...illusion."}
              :title "in the Garden"
              :dir {:1 {:west :Observatory}}
              :contents {:1 #{:diary}}
              :state :1}
   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Level 5
   :Great_Hall {:desc {:1 "Only two exits exist, one to the right and the other to the left. Each exit is guarded by a 5 meter tall guardian."
		       :2 "Only two exits exist, one to the right and the other to the left. Each exit is guarded by a 5 meter tall guardian."
		       :3 "Only two exits exist, one to the right and the other to the left. Each exit is guarded by a 5 meter tall guardian."
		       }
              :title "in the Great Hall"
              :dir {:1 {:right :Heaven
			:left  :Hell}
                    :2 {:right :Heaven
			:left  :Hell}
                    :3 {:right :Heaven
			:left  :Hell}}
              :contents {:1 #{}
			 :2 #{}
			 :3 #{}}
              :state :1}
   :Heaven {:desc {:1 "You have escaped into the human world!"}
              :title "in Heaven."
              :dir {}
              :contents {:1 #{}}
              :state :1}
   :Hell {:desc {:1 "You have been locked into an abyss for eternity!"}
              :title "In Hell."
              :dir {}
              :contents {:1 #{}}
              :state :1}

   })

(def adventurer
  {:location :Entrance_Hall
   :inventory #{}
   :seen #{}
   :pattern [:0 :0 :0 :0]})

(defn status [player temple_map]
  (let [location (-> player :location)
        state (->> temple_map location :state)]
    (println "")
    (println (str "You are " (-> temple_map location :title) ". "))
    (when-not ((-> player :seen) location)
      (println (-> temple_map location :desc state)))
    (update-in player [:seen] #(conj % location))
))

(defn to-keywords [commands]
  (mapv keyword (str/split commands #"[.,?! ]+")))

(defn go [dir player temple_map]
  (let [location (-> player :location)
        state (-> temple_map location :state)
        dest (-> temple_map location :dir state dir)]
    (if (nil? dest)
      (do (println "You can't go that way.")
          player)
      (assoc-in player [:location] dest))))

(defn getItem [item player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (if (not ((-> temple_map location :contents state) item))
      (do (println "Are you hallucinating?")
          player)
      (do (println (str "You've obtained" item ". ")) 
	  (update-in player [:inventory] #(conj % item)))
      )))

(defn useItem [item player temple_map	]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (if (not (contains? (->> player :inventory) item))
      (do (println "You don't have that item.")
          player)
      (do (match [location state item]
             [:Tunnel :1 :torch] (do (println "The spider web burned down!") player)
             [:Corridor :1 :bomb] (if (contains? (-> player :inventory) :lighted_bomb) (do (println "An opening appeared!") player)
										      (do (println "Nothing happened...") player))
             :else (do (println "Nothing happened...") player)
            )))))

(defn useMap [item player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (if (not (contains? (->> player :inventory) item))
      temple_map
      (match [location state item]
             [:Tunnel :1 :torch] (assoc-in temple_map [:Tunnel :state] :2)
             [:Corridor :1 :bomb] (if (contains? (-> player :inventory) :lighted_bomb) (assoc-in temple_map [:Corridor :state] :2) temple_map)
             :else temple_map
            ))))

(defn unlockItem [player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)
        goldKey (contains? (->> player :inventory) :gold_key)
        silverKey (contains? (->> player :inventory) :silver_key)
        largeKey (contains? (->> player :inventory) :large_key)
        smallKey (contains? (->> player :inventory) :small_key)]
    (match [location]
      [:Entrance_Hall]  (do (match [goldKey silverKey]
                                   [true true] (println "You've unlocked the door!")
                                   [false false] (println "You are missing two keys!")
                                   :else (println "You are missing a key!")) player)
      [:Gallery_Hall]   (do (match [largeKey smallKey]
                                   [true true] (println "You've unlocked the door!")
                                   [false false] (println "You are missing two keys!")
                                   :else (println "You are missing a key!")) player)
      :else (do (println "Nothing happened...") player)
    )))

(defn unlockMap [player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)
        goldKey (contains? (-> player :inventory) :gold_key)
        silverKey (contains? (-> player :inventory) :silver_key)
        largeKey (contains? (-> player :inventory) :large_key)
        smallKey (contains? (-> player :inventory) :small_key)]
    (match [location]
      [:Entrance_Hall]  (match [goldKey silverKey]
                               [true true] (assoc-in temple_map [:Entrance_Hall :state] :2)
                               :else temple_map)
      [:Gallery_Hall]   (match [largeKey smallKey]
                               [true true] (assoc-in temple_map [:Gallery_Hall :state] :2)
                               :else temple_map)
      :else temple_map
    )))

(defn pointItem [item player number temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)
        pattern (player :pattern)]
    (if (not ((->> player :inventory) item))	
      (do (println "You don't have that item")
          player)
      (do (if (not (or (= item :wand) (= item :great_wand)))
          (do (println "Nothing happened...")
               player)
          (do (match [location state]
              [:Room_Z :1] (do (println (str "Pillar " number " lit up for a second "))
			   (match [(pattern 1) (pattern 2) (pattern 3) number]
                                  [:2 :1 :3 :4] (do (println "The invisible barrier dispersed! Your wand transfromed into the great wand!") 							 (update-in player [:inventory] #(conj % :great_wand)))
                                  :else (do (assoc-in player [:pattern] (vector (pattern 1) (pattern 2) (pattern 3) number)))))
              [:Room_N :1] (if (contains? (-> player :inventory) :great_wand) (do (println (str "Statue " number " rattled for a second "))
			   (match [(pattern 1) (pattern 2) (pattern 3) number]
                                  [:2 :3 :4 :1] (do (println "The troll disappeared!") (assoc-in temple_map [:Room_N :state] :2) player) 
                                  :else (do (assoc-in player [:pattern] (vector (pattern 1) (pattern 2) (pattern 3) number)))))
			   (do (println "Your wand doesn't have the power to affect statues yet.") player))
              :else (do (println "Nothing happened...") player))
              ))))))

(defn pointMap [item player number temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)
        pattern (player :pattern)]
    (if (not ((->> player :inventory) item))	
      temple_map
      (if (not (= item :wand))
          temple_map
          (do (match [location state]
              [:Room_Z :1] (match [(pattern 1) (pattern 2) (pattern 3) number]
                                  [:2 :1 :3 :4] (assoc-in temple_map [:Room_Z :state] :2)
                                  :else temple_map)
              [:Room_N :1] (match [(pattern 1) (pattern 2) (pattern 3) number]
                                  [:2 :3 :4 :1] (assoc-in temple_map [:Room_N :state] :2) 
                                  :else temple_map)
              :else temple_map))
              ))))

(defn askItem [player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (match [location]
           [:Shrine] (match [state]
			   [:1] (do (println "goblin: bring me the rainbow gem and I shall give you the power to defeat the ogre") player)
			   :else (do (println "goblin: ???") player))
	   [:Music_Hall] (match [state]
			        [:1] (do (println "Old Man: Answer three riddles and I will let you pass.") player)
			        [:2] (do (println "Old Man: It walks on four legs in the morning, two legs at noon, and three legs in the evening. What am I?") player)
			        [:3] (do (println "Old Man: There is a house. One enters it blind and comes out seeing. What is it?") player)
			        [:4] (do (println "Old Man: Voiceless it cries, wingless flutters, toothless bites, mouthless mutters. What is it?") player)
			        [:5] (do (println "Old Man: You may pass.") player))
	   [:Great_Hall] (match [state]
				[:1] (do (println "The Two Guardians: One of us always lies and the other always tells the truth. One door leads to the human world, while the other to an eternal abyss. Choose wisely.") player)
				[:2] (do (println "The Two Guardians: We'll answer one question, but only by pointing.") (println "Question 1 (" "to the right guard): If I asked the other guard which door leads to the human word, which one will he point to?") (println "Question 2 (" "to the left guard): If I asked the other guard which door leads to the human word, which one will he point to?") player)
				[:3] (do (println "The Two Guardians: We have already answered your question.") player)
			)
           :else (do (println "I wonder who you're talking to...") player)
)))

(defn giveItem [item player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (if (not ((->> player :inventory) item))	
      (do (println "You don't have that item")
          player)
      (do (match [location]
          	 [:Shrine] (if (= state :1) (if (= item :rainbow_gem) (do (println "Goblin: The RAINBOW GEM!!!") player) 
								      (do (println "Goblin: That's NOT the RAINBOW GEM!!!") player))
					    (do (println "Goblin: ???") player))
          	 [:Music_Hall] (do (println "Old Man: Bribing won't help you here.") player)
          	 [:Great_Hall] (do (println "Guardian: ...") player)
                 :else (do (println "Nothing happened") player)))
              )))

(defn giveMap [item player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (if (and (= location :Shrine) (= state :1) (= item :rainbow_gem))
	(assoc-in temple_map [:Shrine :state] :2)
	temple_map
              )))

(defn forgeItem [player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)
        sapphire (contains? (-> player :inventory) :sapphire)
        emerald (contains? (-> player :inventory) :emerald)
        ruby (contains? (-> player :inventory) :ruby)]
    (if (= location :Forge) 
	(do (if (and sapphire emerald ruby) 
	       (do (println "You've obtained:rainbow_gem") (update-in player [:inventory] #(conj % :rainbow_gem)))
	       (do (println "You don't have all the required gems") player)))
	(do (println "You can't forge here.") player)
              )))

(defn shootMap [player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (if (not (contains? (-> player :inventory) :bow_and_arrow)) (do (println "You have nothing to shoot with.") temple_map)
    	(if (and (= location :Trophy_Hall) (= state :1))
		(do (println "The ogre died!") (assoc-in temple_map [:Trophy_Hall :state] :2))
		(do (println "Where are you shooting?") temple_map)
              ))))

(defn askMap [player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (match [location state]
	   [:Music_Hall :1] (assoc-in temple_map [:Music_Hall :state] :2)
	   [:Great_Hall :1] (assoc-in temple_map [:Great_Hall :state] :2)
	   :else temple_map
              )))

(defn answerMap [answer player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (match [location]
           [:Music_Hall] (match [state]
			       [:2] (if (= answer :man) (assoc-in temple_map [:Music_Hall :state] :3) temple_map)
			       [:3] (if (= answer :school) (assoc-in temple_map [:Music_Hall :state] :4) temple_map)
			       [:4] (if (= answer :wind) (assoc-in temple_map [:Music_Hall :state] :5) temple_map)
			       :else temple_map)
	   [:Shrine] (do (println "Goblin: That is not a question. It's an ORDER!") player)
	   [:Great_Hall] (do (println "Guardian: ...") player)
           :else (do (println "I wonder who you're talking to...") player)
)))

(defn answerItem [answer player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (match [location]
           [:Music_Hall] (match [state]
			       [:1] (do (println "Old Man: ...") player)
			       [:2] (if (= answer :man) (do (println "Yes!") player) (do (println "No!") player))
			       [:3] (if (= answer :school) (do (println "Right!") player) (do (println "Wrong!") player))
			       [:4] (if (= answer :wind) (do (println "Correct!") player) (do (println "Incorrect!") player))
			       [:5] (do (println "Old Man: ...") player))
	   [:Shrine] (do (println "Goblin: ...") player)
	   [:Great_Hall] (do (println "Guardian: ...") player)
           :else (do (println "I wonder who you're talking to...") player)
)))

(defn readItem [item player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (if (not ((-> temple_map location :contents state) item))
      (do (println "You don't have that item.")
          player)
      (if (or (= item :notebook) (= item :scroll) (= item :diary)) 
	  (match [item] 
		 [:notebook] (do (println "Our species is known as the Homo Sapiens.") player)
		 [:scroll] (do (println "Education is the key to success.") player)
		 [:diary] (do (println "The cool ocean breeze fought the summer heat.") player))
	  (do (println "I can't read that.") player)
      ))))

(defn chooseMap [choice player temple_map]
  (let [location (player :location)
        state (-> temple_map location :state)]
    (match [location]
           [:Great_Hall] (if (= state :2) (match [choice]
			       [:1] (do (println "The right guardian points to the left door") (assoc-in temple_map [:Great_Hall :state] :3))
			       [:2] (do (println "The left guardian points to the left door") (assoc-in temple_map [:Great_Hall :state] :3))
			       :else (do (println "That is not a choice.") temple_map))
				(do (println "You have already chosen.") temple_map))
           :else (do (println "What are you choosing again?") temple_map)
)))

(defn listItem [command player]
    (match [command]
           [:action] (do (println "look, north, south, east, west, right, left, use, get, unlock, point, ask, give, forge, shoot, answer, read, choose, list") player)
	   [:inventory] (do (println (-> player :inventory)) player)
	   [:seen] (do (println (-> player :seen)) player)
           :else (do (println "format:[list action/inventory/seen]") player))
)

(defn respond [player command temple_map]
  (match [(command 0)]
         [:look]   (update-in player [:seen] #(disj % (-> player :location)))
         [:north]  (go :north player temple_map)
         [:south]  (go :south player temple_map)
         [:east]   (go :east player temple_map)
         [:west]   (go :west player temple_map)
	 [:right]  (go :right player temple_map)
	 [:left]   (go :left player temple_map)
         [:n]      (go :north player temple_map)
         [:s]      (go :south player temple_map)
         [:e]      (go :east player temple_map)
         [:w]      (go :west player temple_map)
	 [:r]      (go :right player temple_map)
	 [:l]      (go :left player temple_map)
	 [:go]	   (if (= (count command) 2) (match [(command 1)]
					            [:north]  (go :north player temple_map)
         					    [:south]  (go :south player temple_map)
         				            [:east]   (go :east player temple_map)
         					    [:west]   (go :west player temple_map)
	 					    [:right]  (go :right player temple_map)
	 					    [:left]   (go :left player temple_map)
						    :else     player)
					            (do (println "format:[go direction]") player))
         [:use]    (if (= (count command) 2) (useItem (command 1) player temple_map) (do (println "format:[use item]") player))
         [:get]    (if (= (count command) 2) (getItem (command 1) player temple_map) (do (println "format:[get item]") player))
         [:unlock] (unlockItem player temple_map)
         [:point]  (if (= (count command) 3) (pointItem (command 1) player (command 2) temple_map) 
					     (do (println "format:[point item number]") player))
	 [:ask]    (askItem player temple_map)
	 [:give]   (if (= (count command) 2) (giveItem (command 1) player temple_map) (do (println "format:[give item]") player))
	 [:forge]  (forgeItem player temple_map)
	 [:shoot]  player
	 [:answer] (if (= (count command) 2) (answerItem (command 1) player temple_map) (do (println "format:[answer item]") player))
	 [:read]   (if (= (count command) 2) (readItem (command 1) player temple_map) (do (println "format:[read item]") player))
	 [:choose] player
	 [:list]   (if (= (count command) 2) (listItem (command 1) player) (do (println "format:[list action/inventory/seen]") player))
	 [:light]  (if (= (count command) 2) (if (= (command 1) :bomb) 
						 (update-in player [:inventory] #(conj % :lighted_bomb)) 
						 (do (println "Nothing happened...") player)) 
						 (do (println "format:[answer item]") player))
         :else     (do (println "I don't understand you.") player)
         ))

(defn update [player command temple_map]
  (match [(command 0)]
         [:use]     (if (= (count command) 2) (useMap (command 1) player temple_map) temple_map)
         [:unlock]  (unlockMap player temple_map)
         [:point]   (if (= (count command) 3) (pointMap (command 1) player (command 2) temple_map) temple_map)
	 [:give]    (if (= (count command) 2) (giveMap (command 1) player temple_map) temple_map)
	 [:ask]     (askMap player temple_map)
	 [:shoot]   (shootMap player temple_map)
	 [:answer]  (if (= (count command) 2)  (answerMap (command 1) player temple_map) temple_map)
	 [:choose]  (if (= (count command) 2)  (chooseMap (command 1) player temple_map) temple_map)
         :else      temple_map
         ))

(defn -main
  [& args]
  (loop [local-player adventurer
 	 local-map the-map]
    (if (or (= (-> local-player :location) :Heaven) (= (-> local-player :location) :Hell))
	(match [(-> local-player :location)]
	       [:Heaven] (println "You have escaped the temple! Congratulations.")
	       [:Hell] (println "You are locked in an abyss for all eternity! Game Over."))
	(let [pl (status local-player local-map)
          _  (println "What do you want to do?")
          command (read-line)]
      (recur (respond pl (to-keywords command) local-map) (update pl (to-keywords command) local-map)))
    )))




