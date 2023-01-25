package com.sane4ek.zefirgg.splash.data.model.lal

data class MatchData(
    val info: Info,
    val metadata: Metadata
)

data class Metadata(
    val dataVersion: String,
    val matchId: String,
    val participants: List<String>
)

data class Info(
    val gameCreation: Long,
    val gameDuration: Int,
    val gameEndTimestamp: Long,
    val gameId: Int,
    var gameMode: String,
    val gameName: String,
    val gameStartTimestamp: Long,
    val gameType: String,
    val gameVersion: String,
    val mapId: Int,
    val participants: List<Participant>,
    val platformId: String,
    val queueId: Int,
    val teams: List<Team>,
    val tournamentCode: String
)

//---------------------- Participant ----------------------

data class Participant(
    val allInPings: Int,
    val assistMePings: Int,
    val assists: Int,
    val baitPings: Int,
    val baronKills: Int,
    val basicPings: Int,
    val bountyLevel: Int,
    val challenges: Challenges,
    val champExperience: Int,
    val champLevel: Int,
    val championId: Int,
    val championName: String,
    val championTransform: Int,
    val commandPings: Int,
    val consumablesPurchased: Int,
    val damageDealtToBuildings: Int,
    val damageDealtToObjectives: Int,
    val damageDealtToTurrets: Int,
    val damageSelfMitigated: Int,
    val dangerPings: Int,
    val deaths: Int,
    val detectorWardsPlaced: Int,
    val doubleKills: Int,
    val dragonKills: Int,
    val eligibleForProgression: Boolean,
    val enemyMissingPings: Int,
    val enemyVisionPings: Int,
    val firstBloodAssist: Boolean,
    val firstBloodKill: Boolean,
    val firstTowerAssist: Boolean,
    val firstTowerKill: Boolean,
    val gameEndedInEarlySurrender: Boolean,
    val gameEndedInSurrender: Boolean,
    val getBackPings: Int,
    val goldEarned: Int,
    val goldSpent: Int,
    val holdPings: Int,
    val individualPosition: String,
    val inhibitorKills: Int,
    val inhibitorTakedowns: Int,
    val inhibitorsLost: Int,
    val item0: Int,
    val item1: Int,
    val item2: Int,
    val item3: Int,
    val item4: Int,
    val item5: Int,
    val item6: Int,
    val itemsPurchased: Int,
    val killingSprees: Int,
    val kills: Int,
    val lane: String,
    val largestCriticalStrike: Int,
    val largestKillingSpree: Int,
    val largestMultiKill: Int,
    val longestTimeSpentLiving: Int,
    val magicDamageDealt: Int,
    val magicDamageDealtToChampions: Int,
    val magicDamageTaken: Int,
    val needVisionPings: Int,
    val neutralMinionsKilled: Int,
    val nexusKills: Int,
    val nexusLost: Int,
    val nexusTakedowns: Int,
    val objectivesStolen: Int,
    val objectivesStolenAssists: Int,
    val onMyWayPings: Int,
    val participantId: Int,
    val pentaKills: Int,
    val perks: Perks,
    val physicalDamageDealt: Int,
    val physicalDamageDealtToChampions: Int,
    val physicalDamageTaken: Int,
    val profileIcon: Int,
    val pushPings: Int,
    val puuid: String,
    val quadraKills: Int,
    val riotIdName: String,
    val riotIdTagline: String,
    val role: String,
    val sightWardsBoughtInGame: Int,
    val spell1Casts: Int,
    val spell2Casts: Int,
    val spell3Casts: Int,
    val spell4Casts: Int,
    val summoner1Casts: Int,
    val summoner1Id: Int,
    val summoner2Casts: Int,
    val summoner2Id: Int,
    val summonerId: String,
    val summonerLevel: Int,
    val summonerName: String,
    val teamEarlySurrendered: Boolean,
    val teamId: Int,
    val teamPosition: String,
    val timeCCingOthers: Int,
    val timePlayed: Int,
    val totalDamageDealt: Int,
    val totalDamageDealtToChampions: Int,
    val totalDamageShieldedOnTeammates: Int,
    val totalDamageTaken: Int,
    val totalHeal: Int,
    val totalHealsOnTeammates: Int,
    val totalMinionsKilled: Int,
    val totalTimeCCDealt: Int,
    val totalTimeSpentDead: Int,
    val totalUnitsHealed: Int,
    val tripleKills: Int,
    val trueDamageDealt: Int,
    val trueDamageDealtToChampions: Int,
    val trueDamageTaken: Int,
    val turretKills: Int,
    val turretTakedowns: Int,
    val turretsLost: Int,
    val unrealKills: Int,
    val visionClearedPings: Int,
    val visionScore: Int,
    val visionWardsBoughtInGame: Int,
    val wardsKilled: Int,
    val wardsPlaced: Int,
    val win: Boolean
)

data class Perks(
    val statPerks: StatPerks,
    val styles: List<Style>
)

data class StatPerks(
    val defense: Int,
    val flex: Int,
    val offense: Int
)

data class Style(
    val description: String,
    val selections: List<Selection>,
    val style: Int
)

data class Selection(
    val perk: Int,
    val var1: Int,
    val var2: Int,
    val var3: Int
)
data class Challenges(
    val `12AssistStreakCount`: Double,
    val abilityUses: Double,
    val acesBefore15Minutes: Double,
    val alliedJungleMonsterKills: Double,
    val baronTakedowns: Double,
    val blastConeOppositeOpponentCount: Double,
    val bountyGold: Double,
    val buffsStolen: Double,
    val completeSupportQuestInTime: Double,
    val controlWardsPlaced: Double,
    val damagePerMinute: Double,
    val damageTakenOnTeamPercentage: Double,
    val dancedWithRiftHerald: Double,
    val deathsByEnemyChamps: Double,
    val dodgeSkillShotsSmallWindow: Double,
    val doubleAces: Double,
    val dragonTakedowns: Double,
    val effectiveHealAndShielding: Double,
    val elderDragonKillsWithOpposingSoul: Double,
    val elderDragonMultikills: Double,
    val enemyChampionImmobilizations: Double,
    val enemyJungleMonsterKills: Double,
    val epicMonsterKillsNearEnemyJungler: Double,
    val epicMonsterKillsWithin30SecondsOfSpawn: Double,
    val epicMonsterSteals: Double,
    val epicMonsterStolenWithoutSmite: Double,
    val flawlessAces: Double,
    val fullTeamTakedown: Double,
    val gameLength: Double,
    val getTakedownsInAllLanesEarlyJungleAsLaner: Double,
    val goldPerMinute: Double,
    val hadOpenNexus: Double,
    val highestChampionDamage: Double,
    val highestCrowdControlScore: Double,
    val immobilizeAndKillWithAlly: Double,
    val initialBuffCount: Double,
    val initialCrabCount: Double,
    val jungleCsBefore10Minutes: Double,
    val junglerKillsEarlyJungle: Double,
    val junglerTakedownsNearDamagedEpicMonster: Double,
    val kTurretsDestroyedBeforePlatesFall: Double,
    val kda: Double,
    val killAfterHiddenWithAlly: Double,
    val killedChampTookFullTeamDamageSurvived: Double,
    val killsNearEnemyTurret: Double,
    val killsOnLanersEarlyJungleAsJungler: Double,
    val killsOnOtherLanesEarlyJungleAsLaner: Double,
    val killsOnRecentlyHealedByAramPack: Double,
    val killsUnderOwnTurret: Double,
    val killsWithHelpFromEpicMonster: Double,
    val knockEnemyIntoTeamAndKill: Double,
    val landSkillShotsEarlyGame: Double,
    val laneMinionsFirst10Minutes: Double,
    val legendaryCount: Double,
    val lostAnInhibitor: Double,
    val maxKillDeficit: Double,
    val moreEnemyJungleThanOpponent: Double,
    val multiKillOneSpell: Double,
    val multiTurretRiftHeraldCount: Double,
    val multikills: Double,
    val multikillsAfterAggressiveFlash: Double,
    val outerTurretExecutesBefore10Minutes: Double,
    val outnumberedKills: Double,
    val outnumberedNexusKill: Double,
    val perfectDragonSoulsTaken: Double,
    val perfectGame: Double,
    val pickKillWithAlly: Double,
    val poroExplosions: Double,
    val quickCleanse: Double,
    val quickFirstTurret: Double,
    val quickSoloKills: Double,
    val riftHeraldTakedowns: Double,
    val saveAllyFromDeath: Double,
    val scuttleCrabKills: Double,
    val skillshotsDodged: Double,
    val skillshotsHit: Double,
    val snowballsHit: Double,
    val soloBaronKills: Double,
    val soloKills: Double,
    val stealthWardsPlaced: Double,
    val survivedSingleDigitHpCount: Double,
    val survivedThreeImmobilizesInFight: Double,
    val takedownOnFirstTurret: Double,
    val takedowns: Double,
    val takedownsAfterGainingLevelAdvantage: Double,
    val takedownsBeforeJungleMinionSpawn: Double,
    val takedownsFirstXMinutes: Double,
    val takedownsInAlcove: Double,
    val takedownsInEnemyFountain: Double,
    val teamBaronKills: Double,
    val teamDamagePercentage: Double,
    val teamElderDragonKills: Double,
    val teamRiftHeraldKills: Double,
    val threeWardsOneSweeperCount: Double,
    val tookLargeDamageSurvived: Double,
    val turretPlatesTaken: Double,
    val turretTakedowns: Double,
    val turretsTakenWithRiftHerald: Double,
    val twentyMinionsIn3SecondsCount: Double,
    val unseenRecalls: Double,
    val visionScorePerMinute: Double,
    val wardTakedowns: Double,
    val wardTakedownsBefore20M: Double,
    val wardsGuarded: Double
)

//---------------------- Team ----------------------
data class Team(
    val bans: List<Ban>,
    val objectives: Objectives,
    val teamId: Int,
    val win: Boolean
)

data class Objectives(
    val baron: Baron,
    val champion: Champion,
    val dragon: Dragon,
    val inhibitor: Inhibitor,
    val riftHerald: RiftHerald,
    val tower: Tower
)

data class Tower(
    val first: Boolean,
    val kills: Int
)

data class Champion(
    val first: Boolean,
    val kills: Int
)

data class Dragon(
    val first: Boolean,
    val kills: Int
)

data class Baron(
    val first: Boolean,
    val kills: Int
)

data class Inhibitor(
    val first: Boolean,
    val kills: Int
)

data class RiftHerald(
    val first: Boolean,
    val kills: Int
)

data class Ban(
    val championId: Int,
    val pickTurn: Int
)